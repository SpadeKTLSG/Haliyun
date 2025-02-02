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
     * 用户登录账户密码错误次数 redis key
     */
    String PWD_ERR_CNT_KEY = "sysCache:pwd_err_cnt:";


    /**
     * 验证码Key前缀
     */
    String LOGIN_CODE_KEY = "guest:login:code:";


    /**
     * 验证码过期时间
     */
    Long LOGIN_CODE_TTL_GUEST = 1800L; // 30分钟


    //* Ultra - 限流逻辑 -


    /**
     * 发送验证码时间Key前缀
     */
    String SENDCODE_SENDTIME_KEY = "sms:sendtime:";


    /**
     * 一级限流Key前缀
     */
    String ONE_LEVERLIMIT_KEY = "limit:onelevel:";


    /**
     * 二级限流Key前缀
     */
    String TWO_LEVERLIMIT_KEY = "limit:twolevel:";


}
