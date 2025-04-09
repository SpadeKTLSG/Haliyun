package xyz.spc.serve.data.special;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.spc.common.util.hdfsUtil.HdfsFuncUtil;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.config.mq.TasksMQCompo;
import xyz.spc.serve.data.func.files.FilesFunc;
import xyz.spc.serve.data.func.tasks.DownloadTaskFunc;
import xyz.spc.serve.data.func.tasks.UploadTaskFunc;

import java.io.File;

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


    /**
     * 监听上传任务队列 处理文件导入 HDFS 任务
     * 根据 taskId 从数据库查找任务记录，并使用 HdfsFuncUtil 移动文件到 HDFS
     */
    @RabbitListener(queues =  TasksMQCompo.UPLOAD_QUEUE)
    public void processUploadTask(Long taskId) {


        // TODO：根据 taskId 查询任务记录（UploadTaskDO），这里模拟直接构造文件路径
        // 例如从数据库中查出的文件中转路径：
        // String localFilePath = uploadTaskDO.getLocalFilePath();
        // 模拟为：
        String localFilePath = "D:\\CODE\\HaliyunAll\\DATA\\" + "模拟文件名.txt"; // 替换为实际查到的路径

        // 确定 HDFS 存储的目标路径，建议结合业务确定命名规则
        String hdfsTargetPath = "/user/hdfs/" + new File(localFilePath).getName();

        try {
            // 此处调用 HdfsFuncUtil 中对应方法，将本地文件上传到 HDFS，具体方法依赖你的实现
            // 例如使用 copy/move 功能
            boolean success = HdfsFuncUtil.move(localFilePath, hdfsTargetPath);
            if (success) {
                log.info("任务ID {} 文件成功导入 HDFS, HDFS路径：{}", taskId, hdfsTargetPath);
                // TODO: 更新任务状态为“完成”（状态码 3）至数据库
            } else {
                log.error("任务ID {} 文件导入 HDFS失败", taskId);
                // TODO: 异常补偿、重试等处理
            }
        } catch (Exception e) {
            log.error("任务ID {} 在 HDFS 上传过程异常: {}", taskId, e.getMessage());
            // TODO: 异常处理
        }
    }
}
