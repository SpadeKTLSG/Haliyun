package xyz.spc.serve.auxiliary.config.mq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ 组件配置
 */
@Configuration
public class RabbitMQTopicConfig {


    /**
     * 队列
     */
    @Bean
    public Queue haliQueue() {
        return new Queue(BasicMQCompo.BASIC_QUEUE);
    }


    // 上传任务使用消息队列
    @Bean
    public Queue tasksUploadQueue() {
        return new Queue(TasksMQCompo.UPLOAD_QUEUE);
    }

    // 下载任务使用消息队列
    @Bean
    public Queue tasksDownloadQueue() {
        return new Queue(TasksMQCompo.DOWNLOAD_QUEUE);
    }

    /**
     * 交换机
     */
    @Bean
    public FanoutExchange haliExchange() {
        return new FanoutExchange(BasicMQCompo.BASIC_EXCHANGE);
    }

    // 上传任务使用交换机
    @Bean
    public FanoutExchange tasksUploadExchange() {
        return new FanoutExchange(TasksMQCompo.UPLOAD_EXCHANGE);
    }


    // 下载任务使用交换机
    @Bean
    public FanoutExchange tasksDownloadExchange() {
        return new FanoutExchange(TasksMQCompo.DOWNLOAD_EXCHANGE);
    }


    /**
     * 绑定
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(haliQueue()).to(haliExchange());
    }


    // 上传任务绑定
    @Bean
    public Binding uploadBinding() {
        return BindingBuilder.bind(tasksUploadQueue()).to(tasksUploadExchange());
    }

    // 下载任务绑定
    @Bean
    public Binding downloadBinding() {
        return BindingBuilder.bind(tasksDownloadQueue()).to(tasksDownloadExchange());
    }

}
