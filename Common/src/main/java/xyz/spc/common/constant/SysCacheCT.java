package xyz.spc.common.constant;

/**
 * System key CT
 */
public interface SysCacheCT {
    /**
     * 登录用户 redis key
     */
    String LOGIN_TOKEN_KEY = "sysCache:login_tokens:";

    /**
     * 验证码 redis key
     */
    String CAPTCHA_CODE_KEY = "sysCache:captcha_codes:";

    /**
     * 参数管理 cache key
     */
    String SYS_CONFIG_KEY = "sysCache:sys_config:";

    /**
     * 字典管理 cache key
     */
    String SYS_DICT_KEY = "sysCache:sys_dict:";

    /**
     * 防重提交 redis key
     */
    String REPEAT_SUBMIT_KEY = "sysCache:repeat_submit:";

    /**
     * 限流 redis key
     */
    String RATE_LIMIT_KEY = "sysCache:rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    String PWD_ERR_CNT_KEY = "sysCache:pwd_err_cnt:";
}
