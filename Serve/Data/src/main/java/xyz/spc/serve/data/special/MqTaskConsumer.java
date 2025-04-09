package xyz.spc.serve.data.special;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.spc.domain.model.Data.tasks.UploadTask;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.infra.special.Data.hdfs.HdfsRepo;
import xyz.spc.serve.auxiliary.config.mq.TasksMQCompo;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

import java.util.Map;

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
    public void processUploadTask(Map<String, Object> taskMap) {

        // 获取任务 ID 和本地文件路径
        Long taskId = (Long) taskMap.get("taskId");
        String localFilePath = (String) taskMap.get("localFilePath");


        // ? 从任务表里面获取任务信息 + 文件信息
        Long userId, clusterId;

       UploadTaskGreatVO uploadTaskFunc.getTaskInfo(taskId);

        // ? 确定 HDFS 存储的目标路径, 唯一定位方法为 根目录Path + 用户id + 群组id + 文件名称(内含唯一id)
        String hdfsTargetPath = "/";


        // 此处调用 HdfsFuncUtil 中对应方法

        boolean success = hdfsRepo.

        if (success) {

            // TODO: 更新任务状态为“完成”（状态码 3）至数据库
        } else {
            log.error("任务ID {} 文件导入 HDFS失败", taskId);
            // TODO: 异常补偿、重试等处理
            // todo 发送风控消息到风控模块, 手动补偿 + 系统信息获取
        }

    }
}
