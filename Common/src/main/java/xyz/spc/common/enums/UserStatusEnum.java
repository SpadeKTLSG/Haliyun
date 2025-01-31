package xyz.spc.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户状态
 */
@Data
@AllArgsConstructor
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL("0"),
    /**
     * 停用
     */
    DISABLED("1"),
    /**
     * 禁用
     */
    BANNED("2"),
    /**
     * 删除
     */
    DELETED("3");


    private final String code;

}
