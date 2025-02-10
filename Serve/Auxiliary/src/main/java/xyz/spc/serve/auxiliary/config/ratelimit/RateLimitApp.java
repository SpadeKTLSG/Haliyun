package xyz.spc.serve.auxiliary.config.ratelimit;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import xyz.spc.common.constant.SysCacheKey;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.util.userUtil.IpUtil;
import xyz.spc.serve.auxiliary.common.context.UserContext;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 限流切面应用
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitApp {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisScript<Long> limitRedisScript;

    private final static String SEPARATOR = ":";


    @Pointcut("@annotation(xyz.spc.serve.auxiliary.config.ratelimit.RateLimiter)")
    public void rateLimit() {
    }


    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();


        // 通过工具类 获取 RateLimiter 注解
        RateLimiter rateLimiter = AnnotationUtils.findAnnotation(method, RateLimiter.class);
        if (rateLimiter != null) {

            // 获取注解上的属性
            String key = rateLimiter.key();
            long max = rateLimiter.max();
            long timeout = rateLimiter.timeout();
            TimeUnit timeUnit = rateLimiter.timeUnit();
            LimitTypeEnum limitTypeEnum = rateLimiter.limitType();

            // 默认先用类名+方法名做限流的 key 前缀
            if (StrUtil.isBlank(key)) {
                key = method.getDeclaringClass().getName() + StrUtil.DOT + method.getName();
            }

            judge(key, max, timeout, timeUnit, limitTypeEnum);
        }

        return point.proceed();
    }


    /**
     * 限流判断
     */
    private void judge(String key, long max, long timeout, TimeUnit timeUnit, LimitTypeEnum limitTypeEnum) {

        long ttl = timeUnit.toMillis(timeout);     // 统一使用单位毫秒
        long now = Instant.now().toEpochMilli(); // 当前时间毫秒数
        long expired = now - ttl;
        String limitType = limitTypeEnum.name();

        switch (limitTypeEnum) {
            case IP -> key = SysCacheKey.RATE_LIMIT_KEY + key + SEPARATOR + IpUtil.getIpAddr();
            case TL -> key = SysCacheKey.RATE_LIMIT_KEY + key + SEPARATOR + UserContext.getUA();
        }

        Long executeTimes = stringRedisTemplate.execute(
                limitRedisScript,
                Collections.singletonList(key), now + "", ttl + "", expired + "", max + ""
        );
        Objects.requireNonNull(executeTimes);

        if (executeTimes == 0) {
            log.warn("【{}】限流器 [规则 = {} ] 在 {} 毫秒内已达到其访问上限: {}", key, limitType, ttl, max);
            throw new ClientException("系统繁忙, 请稍后再试");
        } else {
            log.debug("【{}】限流器 [规则 = {} ] 在 {} 毫秒内访问 {} 次", key, limitType, ttl, executeTimes);
        }

    }

}
