package xyz.spc.serve.auxiliary.config.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.spc.common.constant.mq.BasicMQCompo;

/**
 * 默认稳定重试队列
 */
@Configuration
public class StableRetryMQGroup {


    /**
     * 稳定重试交换机
     */
    @Bean
    public FanoutExchange retryExchangeFanout() {
        return new FanoutExchange(BasicMQCompo.RETRY_EXCHANGE, true, false);
    }

    /**
     * 稳定重试队列
     */
    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(BasicMQCompo.RETRY_QUEUE).build();
    }

    /**
     * 绑定
     */
    @Bean
    public Binding bindingRetryQueue(@Qualifier("retryQueue") Queue queue, @Qualifier("retryExchangeFanout") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }


}
