package xyz.spc.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 删除标记枚举
 */
@Data
@AllArgsConstructor
public enum DelEnum {

    /**
     * 正常状态
     */
    NORMAL(0),

    /**
     * 删除状态
     */
    DELETE(1);

    private final Integer statusCode;


}
