package xyz.spc.common.constant;

/**
 * 登录常量
 */
public interface LoginCommonCT {

    /**
     * 默认验证码长度
     */
    int DEFAULT_LENGTH = 4;

    /**
     * 登录用户过期时间
     */
    Long LOGIN_USER_TTL = 360000L; // 10小时 -> 临时扩大
}
