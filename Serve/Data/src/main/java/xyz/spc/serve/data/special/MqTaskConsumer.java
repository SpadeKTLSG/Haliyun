package xyz.spc.serve.data.special;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.constant.UploadDownloadCT;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.gate.vo.Data.tasks.UploadTaskVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.serve.auxiliary.config.mq.TasksMQCompo;
import xyz.spc.serve.data.flow.FilesFlow;
import xyz.spc.serve.data.flow.TasksFlow;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

import java.io.File;
import java.io.InputStream;

import static cn.hutool.core.io.FileUtil.getInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqTaskConsumer {

    // Feign
    private final ClustersClient clustersClient;

    // Flow
    private final TasksFlow tasksFlow;
    private final FilesFlow filesFlow;

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
    public void processUploadTask(Object[] input) {
        // 1 获取任务 ID 和本地文件路径
        Long taskId = (Long) input[0];
        String localFilePath = (String) input[1];
        Long fileId = 0L;
        InputStream is = null;
        String tempFilePath = "";
        try {


            // 2 从任务表里面获取任务信息 + 文件信息
            UploadTaskVO uploadTaskVO = uploadTaskFunc.getTaskInfo(taskId);

            // 3 通过任务信息定位具体的文件信息
            FileGreatVO fileGreatVO = filesFunc.getFileInfo(uploadTaskVO.getFileId());
            fileId = fileGreatVO.getId();

            // 4 确定 HDFS 存储的目标路径, 唯一定位方法为 根目录Path + 用户id + 群组id + 文件名称
            String hdfsTargetPath = "/";
            hdfsTargetPath = hdfsTargetPath + fileGreatVO.getUserId() + "/" + fileGreatVO.getClusterId() + "/" + fileGreatVO.getName();

            // 5 获取到本地磁盘的文件 (流)
            tempFilePath = UploadDownloadCT.UPLOAD_DEFAULT_PATH + localFilePath;
            is = getInputStream(tempFilePath);


            // 6 正式执行上传操作, 更新任务表信息.
            uploadTaskFunc.startTask(taskId);

            // 7 调用 HdfsFuncUtil 中对应方法, 将对应的文件对象上传到 HDFS
            boolean success = hdfsRepo.upload2HDFS(hdfsTargetPath, is);


            if (success) {

                // 8 更新任务表信息为完成
                uploadTaskFunc.completeTask(taskId);

                // 9 记录用户操作日志等日志操作
                // (note: 未来可以根据操作任务表的情况进行日志对应记录顺序. 但是这边因为本地转移到HDFS基本是单机无差别业务, 所以就不考虑了)
                //todo

            } else {
                throw new ServiceException("上传 HDFS 失败"); //手动抛出异常走下方逻辑
            }


            // END
        } catch (Exception e) { // 消息队列中不要抛出异常

            // 0 抛出异常触发下方代码块逻辑, 统一走分布式事务 (最终一致性)

            // 1 记录异常, 这个是 MQ 的处理, 不能进行抛出, 记录日志
            log.error("任务ID {} 本地磁盘文件导入 HDFS失败", taskId, e);

            // 2 (异步) 执行文件表的回滚操作, 可以记录对应文件失败请求信息
            if (fileId == 0L) {
                log.error("文件ID获取失败, 无法进行回滚操作");
                // 重大故障!!!
            }
            filesFlow.rollBackFileCreate4Failure(fileId);

            // 3 (异步) 任务表的回滚操作, 采用断开连接的方式 (谈到避免后续冲突相同上传操作, UUID能进行托底)
            uploadTaskFunc.failTask(taskId);

            // 4 记录用户操作日志等日志操作, 方便后续人工介入
            // todo

            // 5 记录风控日志, 增添风控 Line (条目)

            //todo

            // 6 发送管理员站内用户消息, 提示问题介入
        } finally {
            // 1 关闭流
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    log.error("关闭流失败", e);
                }
            }


            // 2 删除本地磁盘的临时文件
            if(!tempFilePath.isEmpty()) {
                File realLocalTempFile = new File(tempFilePath);
                try {
                    // 删除本地磁盘的临时文件
                    uploadTaskFunc.cleanTempFile(realLocalTempFile);
                } catch (Exception e) {
                    log.error("删除本地磁盘的临时文件失败", e);
                }
            }
        }

    }
}
