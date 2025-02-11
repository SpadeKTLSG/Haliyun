package xyz.spc.domain.dos.Guest.users;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Builder

@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserDO {

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
     * 删除标志 (逻辑删除)
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 乐观锁 version
     */
    @Version
    @TableField
    private Integer version;
}
