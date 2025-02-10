package xyz.spc.common.funcpack.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务端错误码定义
 */
@Getter
@AllArgsConstructor
public enum ServerError {

    //! ========== 一级宏观错误码 服务端错误 ==========
    SERVICE_ERROR("B0000", "系统执行出错"),


    // ========== 二级宏观错误码 系统执行 ==========
    SERVICE_TIMEOUT_ERROR("B1001", "系统执行超时"),
    SERVICE_HIGH_LOAD_ERROR("B1002", "系统高负载"),
    SERVICE_BREAK_ERROR("B1003", "系统熔断"),
    SERVICE_DOWN_ERROR("B1004", "系统宕机"),
    SERVICE_UNKNOW_ERROR("B1005", "系统未知错误"),

    // ========== 二级宏观错误码 系统容灾 ==========
    SERVICE_CIRCUIT_BREAKER_ERROR("B2001", "系统限流"),
    SERVICE_DEGRADE_ERROR("B2002", "系统降级"),
    SERVICE_DEGRADE_BREAK_ERROR("B2003", "系统降级熔断"),
    SERVICE_DEGRADE_DOWN_ERROR("B2004", "系统降级宕机"),
    SERVICE_DEGRADE_UNKNOW_ERROR("B2005", "系统降级未知错误"),

    // ========== 二级宏观错误码 系统资源 ==========
    SERVICE_RESOURCE_ERROR("B3001", "系统资源异常"),
    SERVICE_RESOURCE_OUT_ERROR("B3002", "系统资源耗尽"),
    SERVICE_RESOURCE_NOT_FOUND_ERROR("B3003", "系统资源丢失"),

    // ========== 二级宏观错误码 三方服务 ==========
    REMOTE_ERROR("C0001", "第三方服务出错"),


    // ========== 二级宏观错误码 ... ==========


    UNKNOWN_ERROR("B9999", "未知错误");

    private final String code;

    private final String message;

}
