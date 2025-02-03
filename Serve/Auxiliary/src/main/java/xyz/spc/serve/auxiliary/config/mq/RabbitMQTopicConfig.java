package xyz.spc.serve.auxiliary.config.mq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.spc.common.constant.mqEntity.RabbitMQConstant;


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
        return new Queue(RabbitMQConstant.QUEUE);
    }

    /**
     * 交换机
     */
    @Bean
    public FanoutExchange haliExchange() {
        return new FanoutExchange(RabbitMQConstant.EXCHANGE);
    }

    /**
     * 绑定
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(haliQueue()).to(haliExchange());
    }

}
