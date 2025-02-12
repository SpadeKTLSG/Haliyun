package xyz.spc.domain.dos.Guest.users;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

import java.time.LocalDateTime;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserDO extends BaseDO {


    /**
     * User主键
     */
    private Long id;

    /**
     * 是否是管理 0否
     */
    private Integer admin;

    /**
     * 状态 (0正常, 1停用, 2封禁)
     */
    private Integer status;

    /**
     * 登陆方式 (0账号密码 1手机验证码 2邮箱验证码)
     */
    private Integer loginType;

    /**
     * 账号 (唯一)
     */
    private String account;

    /**
     * 密码
     */
    private String password;


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
