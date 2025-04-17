package xyz.spc.gate.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.spc.common.funcpack.BasePOJO;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDTO extends BasePOJO {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
