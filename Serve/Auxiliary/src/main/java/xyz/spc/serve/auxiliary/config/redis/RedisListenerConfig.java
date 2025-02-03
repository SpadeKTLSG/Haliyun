package xyz.spc.serve.auxiliary.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Redis监听器配置
 */
@Slf4j
@Configuration
public class RedisListenerConfig {

    /**
     * Redis消息监听器容器
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        log.debug("Redis消息监听器容器初始化完成");
        return container;
    }

}
