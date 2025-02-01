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

    public UserDO toDO() {
        UserDO tmp = UserDO.builder()
                .id(id)
                .groupId(groupId)
                .admin(admin)
                .status(status)
                .loginType(loginType)
                .account(account)
                .password(password)
                .build();

        //补充BM的基础信息
        tmp.setCreateTime(getCreateTime());
        tmp.setUpdateTime(getUpdateTime());
        tmp.setDelFlag(getDelFlag());

        return tmp;
    }

    public User fromDO(UserDO userDO) {
        User tmp = User.builder()
                .id(userDO.getId())
                .groupId(userDO.getGroupId())
                .admin(userDO.getAdmin())
                .status(userDO.getStatus())
                .loginType(userDO.getLoginType())
                .account(userDO.getAccount())
                .password(userDO.getPassword())
                .build();

        //补充BM的基础信息
        tmp.setCreateTime(userDO.getCreateTime());
        tmp.setUpdateTime(userDO.getUpdateTime());
        tmp.setDelFlag(userDO.getDelFlag());

        return tmp;
    }


    //! 基础信息

    public boolean isAdmin() {
        return admin == 0;
    }

    public boolean isNormal() {
        return status == 0;
    }


}
