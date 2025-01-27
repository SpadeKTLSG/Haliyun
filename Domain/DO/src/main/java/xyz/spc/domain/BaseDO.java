package xyz.spc.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import xyz.spc.common.pojo.BasePOJO;

import java.util.Date;

@Data
public class BaseDO extends BasePOJO {

    /**
     * 创建时间 (自动填充)
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间 (自动填充)
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

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
