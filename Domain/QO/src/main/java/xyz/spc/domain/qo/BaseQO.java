package xyz.spc.domain.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.spc.common.funcpack.pojo.BasePOJO;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
public class BaseQO extends BasePOJO {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
