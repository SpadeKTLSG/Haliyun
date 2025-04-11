package xyz.spc.serve.auxiliary.config.mq;

/**
 * 文件任务消息队列组件 (上传、下载)
 */
public interface TasksMQCompo {

    /**
     * 上传任务队列
     */
    String UPLOAD_QUEUE = "tasks_upload_queue";

    /**
     * 上传任务交换机
     */
    String UPLOAD_EXCHANGE = "tasks_upload_exchange";

    /**
     * 下载任务队列
     */
    String DOWNLOAD_QUEUE = "tasks_download_queue";

    /**
     * 下载任务交换机
     */
    String DOWNLOAD_EXCHANGE = "tasks_download_exchange";
}
