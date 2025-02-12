package xyz.spc.domain.dos;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.spc.common.funcpack.BasePOJO;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDO extends BasePOJO {

    /* --半自动填充字段已抽取, 为了提升灵活性 -- */

    //update_time / create_time 已隐藏为数据库实现自动填充字段

    /**
     * 删除标志 (手动控制删除) - 需要对象访问而不是builder
     * 删除枚举为 {DelEnum}
     */
    private Integer delFlag;

    /**
     * 乐观锁 version
     */
    @Version
    private Integer version;

}
