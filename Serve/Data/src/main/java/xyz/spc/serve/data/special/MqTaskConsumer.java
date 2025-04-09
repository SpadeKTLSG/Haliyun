package xyz.spc.serve.data.special;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.gate.vo.Data.tasks.UploadTaskVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.serve.auxiliary.config.mq.TasksMQCompo;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import static cn.hutool.core.io.FileUtil.getInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqTaskConsumer {

    // Feign
    private final ClustersClient clustersClient;

    // Func
    private final FilesFunc filesFunc;
    private final DownloadTaskFunc downloadTaskFunc;
    private final UploadTaskFunc uploadTaskFunc;
    private final HdfsRepo hdfsRepo;

    /**
     * ! 上传任务 后续处理 本地 -> HDFS
     * 监听上传任务队列 处理文件导入 HDFS 任务
     * 根据 taskId 从数据库查找任务记录，并使用 HdfsFuncUtil 移动文件到 HDFS
     */
    @RabbitListener(queues = TasksMQCompo.UPLOAD_QUEUE)
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void processUploadTask(Map<String, Object> taskMap) throws Exception {

        // 1 获取任务 ID 和本地文件路径
        Long taskId = (Long) taskMap.get("taskId");
        String localFilePath = (String) taskMap.get("localFilePath");

        // 2 从任务表里面获取任务信息 + 文件信息
        UploadTaskVO uploadTaskVO = uploadTaskFunc.getTaskInfo(taskId);

        // 3 通过任务信息定位具体的文件信息
        FileGreatVO fileGreatVO = filesFunc.getFileInfo(uploadTaskVO.getFileId());

        // 4 确定 HDFS 存储的目标路径, 唯一定位方法为 根目录Path + 用户id + 群组id + 文件名称(内含唯一id)
        String hdfsTargetPath = "/";
        hdfsTargetPath = hdfsTargetPath + fileGreatVO.getUserId() + "/" + fileGreatVO.getClusterId() + "/" + fileGreatVO.getName();

        // 5 获取到本地磁盘的文件 (流)
        File localFile = new File(localFilePath);
        InputStream is = getInputStream(localFile);


        // 6 正式执行上传操作, 更新任务表信息.
        uploadTaskFunc.startTask(taskId);

        //  调用 HdfsFuncUtil 中对应方法, 将对应的文件对象上传到 HDFS
        boolean success = hdfsRepo.upload2HDFS(hdfsTargetPath, is);


        if (success) {


            // 更新任务表信息为完成
            uploadTaskFunc.completeTask(taskId);

            // 记录用户操作日志等日志操作
            // (note: 未来可以根据操作任务表的情况进行日志对应记录顺序. 但是这边因为本地转移到HDFS基本是单机无差别业务, 所以就不考虑了)
            //todo

        } else {
            log.error("任务ID {} 本地磁盘文件导入 HDFS失败", taskId);
            // todo 发送风控消息到风控模块, 手动补偿 + 系统信息获取

            // 失败任务表信息
            uploadTaskFunc.failTask(taskId);
        }

    }
}
