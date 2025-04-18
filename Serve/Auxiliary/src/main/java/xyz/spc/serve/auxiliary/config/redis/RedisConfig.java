package xyz.spc.serve.auxiliary.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import xyz.spc.serve.auxiliary.config.redis.compo.StringRedisTemplateProxy;
import xyz.spc.serve.auxiliary.config.redis.core.RedisDistributedProperties;

import java.time.Duration;
import java.util.Objects;


/**
 * Redis配置
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig {


    /**
     * 配置RedisTemplate
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        // 配置连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        // 定义Jackson2JsonRedisSerializer序列化对象
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        StringRedisSerializer stringSerial = new StringRedisSerializer();
        // redis key 序列化方式使用stringSerial
        template.setKeySerializer(stringSerial);
        // redis value 序列化方式使用jackson
        template.setValueSerializer(stringSerial);
        // redis hash key 序列化方式使用stringSerial
        template.setHashKeySerializer(stringSerial);
        // redis hash value 序列化方式使用jackson
        template.setHashValueSerializer(jacksonSeial);


        template.afterPropertiesSet(); // 初始化操作

        log.debug("RedisTemplate配置完成");
        return template;
    }


    /**
     * 配置StringRedisTemplateProxy
     */
    @Bean
    public StringRedisTemplateProxy stringRedisTemplateProxy(StringRedisTemplate stringRedisTemplate, RedisDistributedProperties redisProperties, RedissonClient redissonClient) {
        return new StringRedisTemplateProxy(stringRedisTemplate, redisProperties, redissonClient);
    }


    /**
     * 配置CacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<Object, Object> template) {
        RedisCacheConfiguration defaultCacheConfiguration =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        // 设置key为String
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
                        // 设置value 为自动转Json的Object
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
                        // 不缓存null
                        .disableCachingNullValues()
                        // 缓存数据保存6小时
                        .entryTtl(Duration.ofHours(6));

        RedisCacheManager redisCacheManager =
                RedisCacheManager.RedisCacheManagerBuilder
                        // Redis 连接工厂
                        .fromConnectionFactory(Objects.requireNonNull(template.getConnectionFactory()))
                        // 缓存配置
                        .cacheDefaults(defaultCacheConfiguration)
                        // 配置同步修改或删除 put/evict
                        .transactionAware()
                        .build();
        log.debug("CacheManager配置完成");
        return redisCacheManager;
    }


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
