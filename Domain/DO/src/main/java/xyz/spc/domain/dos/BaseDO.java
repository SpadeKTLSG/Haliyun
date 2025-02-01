package xyz.spc.domain.dos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.spc.common.funcpack.pojo.BasePOJO;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDO extends BasePOJO {

    /**
     * 创建时间 (自动填充)
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间 (自动填充)
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标志 (自动填充)
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

    /**
     * 乐观锁 version
     */
    @Version
    @TableField
    private Integer version;
}
