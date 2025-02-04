package xyz.spc.common.funcpack.commu.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 客户端错误码定义
 */
@Getter
@RequiredArgsConstructor
public enum ClientError {

    //! ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A0000", "用户端错误"),

    // ========== 二级宏观错误码 用户登陆注册 ==========
    USER_REGISTER_ERROR("A1001", "用户注册错误"),
    USER_NAME_VERIFY_ERROR("A1002", "用户名校验失败"),
    USER_NAME_EXIST_ERROR("A1003", "用户名已存在"),
    USER_NAME_SPECIAL_CHARACTER_ERROR("A1004", "用户名包含特殊字符"),
    PASSWORD_VERIFY_ERROR("A1005", "密码校验失败"),
    PASSWORD_SHORT_ERROR("A1006", "密码长度不够"),
    PHONE_VERIFY_ERROR("A1007", "手机格式校验失败"),
    NOT_LOGIN_ERROR("A1008", "用户未登录"),
    USER_NOT_EXIST_ERROR("A1009", "用户不存在"),
    USER_PASSWORD_ERROR("A1010", "用户密码错误"),
    USER_PHONE_EXIST_ERROR("A1011", "用户手机号已存在"),
    USER_EMAIL_EXIST_ERROR("A1012", "用户邮箱已存在"),
    USER_EMAIL_VERIFY_ERROR("A1013", "用户邮箱格式校验失败"),
    USER_PHONE_NOT_EXIST_ERROR("A1014", "用户手机号不存在"),
    USER_EMAIL_NOT_EXIST_ERROR("A1015", "用户邮箱不存在"),
    USER_PHONE_VERIFY_CODE_ERROR("A1016", "用户手机验证码错误"),
    USER_EMAIL_VERIFY_CODE_ERROR("A1017", "用户邮箱验证码错误"),
    USER_ACCOUNT_BLOCKED_ERROR("A1018", "用户账号被冻结"),
    USER_ACCOUNT_DELETED_ERROR("A1019", "用户账号已删除"),
    USER_ACCOUNT_NOT_EXIST_ERROR("A1020", "用户账号不存在"),
    USER_LOGIN_ERROR("A1021", "用户登陆错误"),
    USER_CODE_ERROR("A1022", "用户验证码错误"),

    // ========== 二级宏观错误码 用户权限 ==========
    USER_AUTH_ERROR("A2001", "用户无权限"),
    USER_AUTH_EXPIRED_ERROR("A2002", "用户权限过期"),
    USER_AUTH_UNKOWN_ERROR("A2003", "用户权限未知错误"),

    // ========== 二级宏观错误码 用户请求参数 ==========
    USER_PARAM_ERROR("A3001", "用户请求参数错误"),

    // ========== 二级宏观错误码 ... ==========


    UNKNOWN_ERROR("A9999", "未知错误");

    private final String code;

    private final String message;

}
