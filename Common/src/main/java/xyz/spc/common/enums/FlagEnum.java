package xyz.spc.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 标识枚举, 关联 0/1 T/F
 */
@Data
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
