package xyz.spc.domain.dos.Guest.users;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

import java.time.LocalDateTime;

/**
 * 用户功能 DO
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_func")
public class UserFuncDO extends BaseDO {


    /**
     * User主键
     */
    private Long id;

    /**
     * Level主键
     */
    private Long levelId;

    /**
     * 是否VIP 0否 1是
     */
    private Integer vip;

    /**
     * 当前创建群组数
     */
    private Integer createGroupCount;

    /**
     * 最大创建群组数 (初始上限VIP100个, 普通10个, 可增加)
     */
    private Integer createGroupMax;

    /**
     * 当前加入群组数
     */
    private Integer joinGroupCount;

    /**
     * 最大加入群组数 (初始上限VIP1000个, 普通100个, 可增加)
     */
    private Integer joinGroupMax;

    /**
     * 硬币数 (指平台硬币)
     */
    private Long coin;

    /**
     * (换算的)能量币数 (指群内硬币的估价总额)
     */
    private Long energyCoin;

    /**
     * 注册邀请码
     */
    private String registerCode;


    /* -- 以下为半自动填充字段 -- */


    /**
     * 创建时间 (半自动填充)
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    /**
     * 修改时间 (半自动填充)
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标志 (半自动填充)
     */
    @TableField(fill = FieldFill.DEFAULT)
    private Integer delFlag;

    /**
     * 乐观锁 version
     */
    @Version
    private Integer version;
}
