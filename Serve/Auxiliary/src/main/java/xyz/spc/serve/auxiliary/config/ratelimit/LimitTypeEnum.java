package xyz.spc.serve.auxiliary.config.ratelimit;

/**
 * 限流类型
 */

public enum LimitTypeEnum {
    /**
     * 用户TL限流 (本质上是User的UA : Account)
     */
    TL,

    /**
     * 根据请求者IP进行限流
     */
    IP
}
