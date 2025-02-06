package xyz.spc.common.constant.Guest.users;

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

    /**
     * 验证码过期时间
     */
    Long LOGIN_CODE_TTL_GUEST = 1800L; // 30分钟

    /**
     * 密码输入错误次数限制
     */
    Long PWD_ERR_CNT_LIMIT = 5L;

    /**
     * 密码输错记录时间 (DAY)
     */
    Long PWD_ERR_CNT_FREEZE_TIME = 1L; // 1天


    /**
     * 默认注册用户位置
     */
    String DEFAULT_REGISTER_LOCATION = "门头沟";

    /**
     * 默认注册用户介绍
     */
    String DEFAULT_REGISTER_INTRODUCE = "这个人很懒，什么都没有留下";
}
