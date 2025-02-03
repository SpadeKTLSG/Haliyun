package xyz.spc.serve.auxiliary.config.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
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


    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate(); // 重试模板
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(); // 重试策略
        retryPolicy.setMaxAttempts(); // 最大重试次数
        retryTemplate.setRetryPolicy(retryPolicy);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(5000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
