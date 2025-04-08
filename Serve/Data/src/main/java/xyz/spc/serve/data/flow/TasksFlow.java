package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.config.mq.TasksMQCompo;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

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

    // MQ
    private final RabbitTemplate mqProducer;

    /**
     * 上传文件流处理
     *
     * @param file      上传的文件对象
     * @param clusterId 群组id
     */
    public void uploadFile(MultipartFile file, Long clusterId, Long userId) {

        // 实现上传文件中转存储对应机器位置
        uploadTaskFunc.handleTempUpload(file, userId, clusterId);

        // 任务记录创建到批处理表, 生成对应记录
        Long taskId = uploadTaskFunc.taskGen(file, userId, clusterId);

        // 调用 MQ 发送消息, 消息仅仅包含 Long 任务id, 直接从数据库找出对应行的任务进行异步的 本地缓存 -> HDFS 即可
        mqProducer.convertAndSend(
                TasksMQCompo.UPLOAD_EXCHANGE,
                "", //简单绑定不需要 routingKey
                taskId
        );
    }
}
