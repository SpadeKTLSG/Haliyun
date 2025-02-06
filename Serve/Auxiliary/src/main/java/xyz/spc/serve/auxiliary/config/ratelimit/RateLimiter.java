package xyz.spc.serve.auxiliary.config.ratelimit;


import xyz.spc.common.constant.SysCacheKey;

import java.lang.annotation.*;

/**
 * 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    String key() default SysCacheKey.RATE_LIMIT_KEY;

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitTypeEnum limitType() default LimitTypeEnum.DEFAULT;
}
