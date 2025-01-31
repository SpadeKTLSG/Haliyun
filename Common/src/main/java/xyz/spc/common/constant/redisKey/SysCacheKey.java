package xyz.spc.common.constant.redisKey;

/**
 * System Cache key
 */
public interface SysCacheKey {


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

}
