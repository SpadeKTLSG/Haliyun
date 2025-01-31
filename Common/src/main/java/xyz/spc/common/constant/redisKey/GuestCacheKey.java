package xyz.spc.common.constant.redisKey;

/**
 * 用户 Cache Key
 */
public interface GuestCacheKey {

    /**
     * 用户 ID Key
     */
    String USER_ID_KEY = "userId";

    /**
     * 用户名 Key
     */
    String USER_NAME_KEY = "username";

    /**
     * 用户真实名称 Key
     */
    String REAL_NAME_KEY = "realname";

    /**
     * 用户 Token Key
     */
    String USER_TOKEN_KEY = "token";

    /**
     * 用户验证码 redis key
     */
    String CAPTCHA_CODE_KEY = "sysCache:captcha_codes:";

    /**
     * 用户登录账户密码错误次数 redis key
     */
    String PWD_ERR_CNT_KEY = "sysCache:pwd_err_cnt:";

}
