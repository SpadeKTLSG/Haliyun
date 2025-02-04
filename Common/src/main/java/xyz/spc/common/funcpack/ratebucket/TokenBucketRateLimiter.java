package xyz.spc.common.funcpack.ratebucket;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucketRateLimiter {

    private final int bucketCapacity; // 令牌桶的容量
    private final AtomicInteger tokens; // 当前令牌数
    private final int refillRate; // 令牌生成速率（每秒生成的令牌数）
    private long nextRefillTime; // 下次生成令牌的时间
    private final RLock lock; // 分布式锁

    public TokenBucketRateLimiter(RedissonClient redissonClient) {
        this(RateBucketCT.DEFAULT_CAPACITY, RateBucketCT.DEFAULT_RATE, redissonClient);
    }

    public TokenBucketRateLimiter(int bucketCapacity, int refillRate, RedissonClient redissonClient) {
        this.bucketCapacity = bucketCapacity;
        this.refillRate = refillRate;
        this.tokens = new AtomicInteger(bucketCapacity);
        this.nextRefillTime = System.currentTimeMillis();
        this.lock = redissonClient.getLock("tokenBucketLock");
    }

    public boolean tryAcquire() {
        return tryAcquire(1);
    }

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

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://4").setPassword("23");

        RedissonClient redissonClient = Redisson.create(config);

        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(5, 2, redissonClient);

        for (int i = 0; i < 100; i++) {
            boolean allowed = rateLimiter.tryAcquire();
            if (!allowed) {
                System.out.println("请求 " + i + " 被拒绝");
            } else {
                System.out.println("请求 " + i + " 获得令牌，允许通过");
            }
            try {
                Thread.sleep(200); // 模拟请求间隔
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
