package xyz.spc.domain.model.Guest.users;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.model.BaseModel;
import xyz.spc.gate.dto.Guest.users.UserDTO;

/**
 * 用户
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
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
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_STOP = 1;
    public static final int STATUS_BAN = 2;


    /**
     * 登陆方式 (0账号密码 1手机验证码 2邮箱验证码 3账号密码 + 手机验证码)
     */
    private Integer loginType;
    public static final int LOGIN_TYPE_ACCOUNT = 0;
    public static final int LOGIN_TYPE_PHONE = 1;
    public static final int LOGIN_TYPE_EMAIL = 2;
    public static final int LOGIN_TYPE_ACCOUNT_PHONE = 3;

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

        tmp.setCreateTime(userDO.getCreateTime());
        tmp.setUpdateTime(userDO.getUpdateTime());
        tmp.setDelFlag(userDO.getDelFlag());

        return tmp;
    }

    public UserDTO toDTO() {
        UserDTO tmp = UserDTO.builder()
                .id(id)
                .groupId(groupId)
                .admin(admin)
                .status(status)
                .loginType(loginType)
                .account(account)
                .password(password)
                .build();

        return tmp;
    }

    public User fromDTO(UserDTO userDTO) {
        User tmp = User.builder()
                .id(userDTO.getId())
                .groupId(userDTO.getGroupId())
                .admin(userDTO.getAdmin())
                .status(userDTO.getStatus())
                .loginType(userDTO.getLoginType())
                .account(userDTO.getAccount())
                .password(userDTO.getPassword())
                .build();

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
