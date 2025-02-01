package xyz.spc.domain.model.Guest.users;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.model.BaseModel;

/**
 * 用户
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseModel {

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


    //? 转换器

    @Override
    public UserDO toDO() {
        return UsersMapper.INSTANCE.toUserDO(this);
    }


    //! 基础信息

    public boolean isAdmin() {
        return admin == 0;
    }

    public boolean isNormal() {
        return status == 0;
    }


}
