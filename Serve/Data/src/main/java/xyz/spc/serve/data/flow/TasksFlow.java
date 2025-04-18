package xyz.spc.serve.data.flow;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.gate.dto.Data.files.FileDTO;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.serve.auxiliary.config.mq.TasksMQCompo;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksFlow {


    // Feign
    private final ClustersClient clustersClient;

    // Func
    private final FilesFunc filesFunc;
    private final DownloadTaskFunc downloadTaskFunc;
    private final UploadTaskFunc uploadTaskFunc;

    private final HdfsRepo hdfsRepo;

    // MQ
    private final RabbitTemplate mqProducer;


    /**
     * 上传文件流处理
     *
     * @param file      上传的文件对象
     * @param clusterId 群组id
     */
    @Transactional(rollbackFor = Exception.class, timeout = 50)
    public void uploadFile(MultipartFile file, Long clusterId, Long userId) throws IOException {

        // 1 实现上传文件中转存储对应机器位置
        String localFilePath = uploadTaskFunc.handleTempUpload(file, userId, clusterId);

        // 2 创建对应的文件对象 (临时) - 组装入参
        String type = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        FileDTO tmp = FileDTO.builder()
                .name(file.getOriginalFilename())
                .type(type)
                .userId(userId)
                .clusterId(clusterId)
                .build();

        Long file_id = filesFunc.createFile(tmp, file.getSize());

        // 3 任务记录创建到批处理表, 生成对应记录
        Long taskId = uploadTaskFunc.taskGen(file_id, file.getOriginalFilename(), userId);

        // 4 简单封装任务id和本地文件路径到发送的消息中
        // 调用 MQ 发送消息, 从数据库找出对应行的任务进行异步的 本地缓存 -> HDFS 即可
        mqProducer.convertAndSend(
                TasksMQCompo.UPLOAD_EXCHANGE,
                "", //简单绑定不需要 routingKey
                new Object[]{taskId, localFilePath}
        );


        //? note 这里涉及到分布式事务问题. 如果上传 HDFS 失败了, 那么任务处理表状态应该是失败的, 但是文件表却写了进去
        // 因此, 我们需要在上传 HDFS 失败的情况下, 将文件表的记录删除掉(回滚)
        // 已经实现.
    }


    /**
     * 下载文件流处理
     */
    @Transactional(rollbackFor = Exception.class, timeout = 50)
    public void downloadFile(Long fileId, Long creatorUserId, Long fromClusterId, HttpServletResponse response) {


        // 1 获取对应文件对象
        FileGreatVO fileGreatVO = filesFunc.getFileInfo(fileId);
        String fileName = fileGreatVO.getName(); // 文件名称


        // 2 创建对应下载任务
        downloadTaskFunc.taskGen(fileId, fileName, creatorUserId);


        // 3 发起 HDFS 下载请求到本地磁盘缓存
        String firstFileDiskPath = downloadTaskFunc.handleTempDownload(fileName, creatorUserId, fromClusterId);


        // 4 定位到对应文件对象 + 进行本地缓存的文件对象重命名避免冲突
        File realLocalTempFile = downloadTaskFunc.locateRenameFile(fileName, firstFileDiskPath, creatorUserId, fromClusterId);


        // 5 (异步) 登记下载次数等信息
        filesFunc.addUserDownloadTimes(fileId);


        // 6 下载任务表登记完成
        downloadTaskFunc.completeTask(fileId);

        // 7 文件返回用户综合
        downloadTaskFunc.download2Client(realLocalTempFile, response);


        // 8 (异步 + 延迟)  清理对应的临时文件
        downloadTaskFunc.cleanTempFile(realLocalTempFile);

    }

    /**
     * 下载文件流处理
     */
    @Transactional(rollbackFor = Exception.class, timeout = 50)
    public File downloadFileNotResp(Long fileId, Long creatorUserId, Long fromClusterId) {


        // 1 获取对应文件对象
        FileGreatVO fileGreatVO = filesFunc.getFileInfo(fileId);
        String fileName = fileGreatVO.getName(); // 文件名称


        // 2 创建对应下载任务
        downloadTaskFunc.taskGen(fileId, fileName, creatorUserId);


        // 3 发起 HDFS 下载请求到本地磁盘缓存
        String firstFileDiskPath = downloadTaskFunc.handleTempDownload(fileName, creatorUserId, fromClusterId);


        // 4 定位到对应文件对象 + 进行本地缓存的文件对象重命名避免冲突
        File realLocalTempFile = downloadTaskFunc.locateRenameFile(fileName, firstFileDiskPath, creatorUserId, fromClusterId);


        // 5 (异步) 登记下载次数等信息
        filesFunc.addUserDownloadTimes(fileId);


        // 6 下载任务表登记完成
        downloadTaskFunc.completeTask(fileId);


        // 7 (异步 + 延迟) 清理对应的临时文件
        downloadTaskFunc.cleanTempFile(realLocalTempFile);


        // 8 返回文件对象用于汇总结果 (走统一压缩逻辑)
        return realLocalTempFile;
    }


    /**
     * 分享文件处理
     */
    @Transactional(rollbackFor = Exception.class, timeout = 50)
    public void shareFile(Long fileId, Long targetClusterId) {

        // 1 获取对应源文件对象
        FileGreatVO fileGreatVO = filesFunc.getFileInfo(fileId);

        // 2 处理修改选项: 目前默认基本不变, 只是做完整拷贝

        // 3 落库文件对象
        filesFunc.cpFile(fileGreatVO, targetClusterId);

        // 4 确定源文件 HDFS 存储的目标路径, 唯一定位方法为 根目录Path + 用户id + 群组id + 文件名称
        String hdfsSourcePath = "/";
        hdfsSourcePath = hdfsSourcePath + fileGreatVO.getUserId() + "/" + fileGreatVO.getClusterId() + "/" + fileGreatVO.getName();

        // 5 确定目标文件 HDFS 存储的目标路径, 唯一定位方法为 根目录Path + 用户id + 目标群组id + 文件名称
        String hdfsTargetPath = "/";
        hdfsTargetPath = hdfsTargetPath + fileGreatVO.getUserId() + "/" + targetClusterId + "/" + fileGreatVO.getName();

        // 6  调用 HdfsFuncUtil 中对应方法, 将对应的文件对象在 HDFS 拷贝
        boolean success = hdfsRepo.copyByPath(hdfsSourcePath, hdfsTargetPath);

        if (!success) {
            throw new ServiceException("HDFS 拷贝失败");
        }

    }

}
