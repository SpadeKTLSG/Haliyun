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
    String LOGIN_CODE_KEY = "Guests:users:login:code:";

    /**
     * 登录请求唯一性Account Key前缀
     */
    String LOGIN_REQUEST_ONLY_KEY = "Guests:users:login:login_only:";

    /**
     * 注册请求唯一性Account Key前缀
     */
    String REGISTER_REQUEST_ONLY_KEY = "Guests:users:login:register_only:";

    /**
     * 发送验证码时间Key前缀
     */
    String SENDCODE_SENDTIME_KEY = "Guests:users:login:sms:sendtime:";


    /**
     * 验证码一级限流Key前缀
     */
    String ONE_LEVERLIMIT_KEY = "Guests:users:login:sms:limit:onelevel:";


    /**
     * 验证码二级限流Key前缀
     */
    String TWO_LEVERLIMIT_KEY = "Guests:users:login:sms:limit:twolevel:";


}
