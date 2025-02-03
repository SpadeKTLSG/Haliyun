package xyz.spc.serve.auxiliary.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ配置
 */
@Slf4j
@EnableRabbit
@Configuration
public class RabbitMQConfig {


    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        log.debug("RabbitTemplate已配置");
        return new RabbitTemplate(connectionFactory);
    }
}
