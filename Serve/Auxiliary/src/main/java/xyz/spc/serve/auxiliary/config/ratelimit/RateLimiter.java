package xyz.spc.serve.auxiliary.config.ratelimit;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 默认请求次数
     */
    long DEFAULT_REQUEST_COUNT = 1000;

    /**
     * 限流key
     */
    String key() default "";

    /**
     * 超时时长, 默认1分钟
     */
    long timeout() default 1;

    /**
     * max 最大请求数
     */
    @AliasFor("max") long value() default DEFAULT_REQUEST_COUNT;

    /**
     * 超时时间单位, 默认 分钟
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * max 最大请求数
     */
    @AliasFor("value") long max() default DEFAULT_REQUEST_COUNT;

    /**
     * 限流类型 - 需要判断进行针对性处理. 默认 TL
     */
    LimitTypeEnum limitType() default LimitTypeEnum.TL;
}
