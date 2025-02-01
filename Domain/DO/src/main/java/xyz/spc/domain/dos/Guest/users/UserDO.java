package xyz.spc.domain.dos.Guest.users;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.BaseDO;

/**
 * 用户 DO
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class UserDO extends BaseDO {

    /**
     * User主键
     */
    private Long id;

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 是否是管理 0否
     */
    private Boolean admin;

    /**
     * 状态 (0正常, 1停用, 2封禁)
     */
    private Integer status;

    /**
     * 登陆方式 (0账号密码 1手机验证码 2邮箱验证码)
     */
    private Integer loginType;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;
}
