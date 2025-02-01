package xyz.spc.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.spc.common.funcpack.pojo.BasePOJO;
import xyz.spc.domain.dos.BaseDO;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseModel extends BasePOJO {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标志
     */
    private Integer delFlag;

    /**
     * Model 2 Do convertor
     */
    public BaseDO toDO() {
        BaseDO baseDO = new BaseDO();
        BaseMapper.INSTANCE.model2DO(this, baseDO);
        return baseDO;
    }
}
