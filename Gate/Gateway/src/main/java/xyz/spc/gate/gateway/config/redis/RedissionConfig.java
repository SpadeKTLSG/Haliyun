package xyz.spc.gate.gateway.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 */
@Slf4j
@Configuration
public class RedissionConfig {


    @Value("${infrastructure.redis.host}")
    private String host;

    @Value("${infrastructure.redis.port}")
    private int port;

    @Value("${infrastructure.redis.password}")
    private String password;


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password);
        log.debug("Redisson配置初始化完成");
        return Redisson.create(config);
    }
}
