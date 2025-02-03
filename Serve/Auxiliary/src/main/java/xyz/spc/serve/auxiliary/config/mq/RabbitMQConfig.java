package xyz.spc.serve.auxiliary.config.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import xyz.spc.common.constant.mq.SystemMQCT;


/**
 * RabbitMQ配置
 */
@Slf4j
@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RetryTemplate retryTemplate;

    /**
     * RabbitMQ模板
     */
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRetryTemplate(retryTemplate); // 设置重试模板

        log.debug("RabbitTemplate已配置");
        return rabbitTemplate;
    }


    /**
     * RabbitMQ重试模板
     */
    @Bean
    public RetryTemplate retryTemplate() {
        // 重试模板
        RetryTemplate retryTemplate = new RetryTemplate();

        // 重试策略
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(SystemMQCT.retryTimes); // 最大重试次数


        // 重试间隔策略
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(SystemMQCT.retryInitialInterval); // 初始间隔
        backOffPolicy.setMultiplier(SystemMQCT.retryMultiplier); // 乘数
        backOffPolicy.setMaxInterval(SystemMQCT.retryMaxInterval); // 最大间隔


        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
