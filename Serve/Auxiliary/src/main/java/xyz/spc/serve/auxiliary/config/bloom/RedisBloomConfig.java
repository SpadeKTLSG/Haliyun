package xyz.spc.serve.auxiliary.config.bloom;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.spc.serve.auxiliary.config.bloom.compo.BloomFilterPenetrateProperties;
import xyz.spc.serve.auxiliary.config.bloom.compo.BloomFilterUserRegisterProperties;

/**
 * Redis布隆过滤器配置
 */
@Slf4j
@Configuration
public class RedisBloomConfig {


    /**
     * 防止缓存穿透的布隆过滤器
     */
    @Bean
    @ConditionalOnProperty(prefix = BloomFilterPenetrateProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public RBloomFilter<String> cachePenetrationBloomFilter(RedissonClient redissonClient, BloomFilterPenetrateProperties bloomFilterPenetrateProperties) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter(bloomFilterPenetrateProperties.getName());
        cachePenetrationBloomFilter.tryInit(bloomFilterPenetrateProperties.getExpectedInsertions(), bloomFilterPenetrateProperties.getFalseProbability());

        log.debug("防止缓存穿透布隆过滤器 配置完成");
        return cachePenetrationBloomFilter;
    }

    /**
     * 防止用户注册重插的布隆过滤器
     */
    @Bean
    @ConditionalOnProperty(prefix = BloomFilterUserRegisterProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient, BloomFilterUserRegisterProperties bloomFilterUserRegisterProperties) {
        RBloomFilter<String> userRegisterCachePenetrationBloomFilter = redissonClient.getBloomFilter(bloomFilterUserRegisterProperties.getName());
        userRegisterCachePenetrationBloomFilter.tryInit(bloomFilterUserRegisterProperties.getExpectedInsertions(), bloomFilterUserRegisterProperties.getFalseProbability());

        log.debug("防止用户注册重插的布隆过滤器配置完成");
        return userRegisterCachePenetrationBloomFilter;
    }
}
