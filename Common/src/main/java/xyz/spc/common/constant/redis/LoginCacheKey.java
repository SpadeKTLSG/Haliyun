package xyz.spc.common.constant.redis;

/**
 * 登录 Cache Key
 */
public interface LoginCacheKey {

    /**
     * 登录 TOKEN Key前缀
     */
    String LOGIN_USER_KEY = "guest:login:token:";

    /**
     * 登陆用户DTO实体前缀 todo
     */
    String LOGIN_USER_DTO_KEY = "sysCache:login_user:dto:";

    /**
     * 登陆用户ID实体前缀
     */
    String LOGIN_USER_ID_KEY = "sysCache:login_user:id:";

    /**
     * 登陆用户ACCOUNT实体前缀
     */
    String LOGIN_USER_ACCOUNT_KEY = "sysCache:login_user:account:";

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


    /**
     * 发送验证码时间Key前缀
     */
    String SENDCODE_SENDTIME_KEY = "sms:sendtime:";


    /**
     * 验证码一级限流Key前缀
     */
    String ONE_LEVERLIMIT_KEY = "limit:onelevel:";


    /**
     * 验证码二级限流Key前缀
     */
    String TWO_LEVERLIMIT_KEY = "limit:twolevel:";

    /**
     * 登录请求唯一性Key前缀 ( + 用户账号)
     */
    String LOGIN_REQUEST_ONLY_KEY = "sysCache:login_request_only:";
}
