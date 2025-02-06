package xyz.spc.common.constant.Guest.users;

/**
 * 登录 Cache Key
 */
public interface LoginCacheKey {

    /**
     * 登录 TOKEN Key前缀
     */
    String LOGIN_USER_KEY = "Guests:users:login:user_token:";

    /**
     * 用户登录账户密码错误次数
     */
    String PWD_ERR_CNT_KEY = "Guests:users:login:pwd_err_cnt:";

    /**
     * 验证码Key前缀
     */
    String LOGIN_CODE_KEY = "guest:login:code:";

    String REGISTER_ONLY_REDISSION_CODE = "sysCache:register_only_redission_code:";

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
