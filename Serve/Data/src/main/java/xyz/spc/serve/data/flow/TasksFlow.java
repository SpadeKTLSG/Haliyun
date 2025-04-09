package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.gate.dto.Data.files.FileDTO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.config.mq.TasksMQCompo;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    // MQ
    private final RabbitTemplate mqProducer;

    /**
     * 上传文件流处理
     *
     * @param file      上传的文件对象
     * @param clusterId 群组id
     */
    public void uploadFile(MultipartFile file, Long clusterId, Long userId) throws IOException {

        // 实现上传文件中转存储对应机器位置
        String localFilePath = uploadTaskFunc.handleTempUpload(file, userId, clusterId);

        // 创建对应的文件对象 (临时) - 组装入参
        String type = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        FileDTO tmp = FileDTO.builder()
                .name(file.getOriginalFilename())
                .type(type)
                .userId(userId)
                .clusterId(clusterId)
                .build();

        Long file_id = filesFunc.createFile(tmp, file.getSize());

        // 任务记录创建到批处理表, 生成对应记录
        Long taskId = uploadTaskFunc.taskGen(file_id, file.getOriginalFilename(), userId);

        // 简单封装任务id和本地文件路径到发送的消息中
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("taskId", taskId);
        taskMap.put("localFilePath", localFilePath);

        // 调用 MQ 发送消息, 消息仅仅包含 Long 任务id, 直接从数据库找出对应行的任务进行异步的 本地缓存 -> HDFS 即可
        mqProducer.convertAndSend(
                TasksMQCompo.UPLOAD_EXCHANGE,
                "", //简单绑定不需要 routingKey
                taskMap
        );
    }
}
