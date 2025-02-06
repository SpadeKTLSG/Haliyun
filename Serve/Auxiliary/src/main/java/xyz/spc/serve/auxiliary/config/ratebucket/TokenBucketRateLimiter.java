package xyz.spc.serve.auxiliary.config.ratebucket;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class TokenBucketRateLimiter {

    private final int bucketCapacity; // 令牌桶的容量
    private final AtomicInteger tokens; // 当前令牌数
    private final int refillRate; // 令牌生成速率（每秒生成的令牌数）
    private long nextRefillTime; // 下次生成令牌的时间
    private final RLock lock; // 分布式锁

    @Autowired
    public TokenBucketRateLimiter(RedissonClient redissonClient) {
        this(redissonClient, RateBucketCT.DEFAULT_CAPACITY, RateBucketCT.DEFAULT_RATE);
    }

    public TokenBucketRateLimiter(RedissonClient redissonClient, int bucketCapacity, int refillRate) {
        this.bucketCapacity = bucketCapacity;
        this.refillRate = refillRate;
        this.tokens = new AtomicInteger(bucketCapacity);
        this.nextRefillTime = System.currentTimeMillis();
        this.lock = redissonClient.getLock("tokenBucketLock");
    }

    /**
     * 尝试获取令牌
     */
    public boolean tryAcquire() {
        return tryAcquire(1);
    }

    /**
     * 尝试获取令牌
     *
     * @param permits 令牌数
     */
    public boolean tryAcquire(int permits) {
        try {
            if (lock.tryLock(500, 10000, TimeUnit.MILLISECONDS)) {
                try {
                    long nowTime = System.currentTimeMillis();
                    refillTokens(nowTime);

                    if (tokens.get() >= permits) {
                        tokens.addAndGet(-permits);
                        return true;
                    }
                    return false;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * 延迟惰性生成令牌
     */
    private void refillTokens(long nowTime) {
        if (nowTime > nextRefillTime) {
            long interval = nowTime - nextRefillTime;
            int newTokens = (int) (interval / 1000 * refillRate);
            if (newTokens > 0) {
                tokens.addAndGet(newTokens); // 生成令牌
                if (tokens.get() > bucketCapacity) { // 令牌数不能超过桶的容量
                    tokens.set(bucketCapacity);
                }
                nextRefillTime = nowTime;
            }
        }
    }
}
