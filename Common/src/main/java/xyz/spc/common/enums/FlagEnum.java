package xyz.spc.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标识枚举, 关联 0/1 T/F
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {

    /**
     * FALSE
     */
    FALSE(0),

    /**
     * TRUE
     */
    TRUE(1);


    private final Integer flag;

}
