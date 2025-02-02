package xyz.spc.domain.model.Guest.users;

import lombok.*;
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


    //? 转换器

    public boolean isAdmin() {
        return admin == 0;
    }

    public boolean isNormal() {
        return status == 0;
    }


    //! 基础信息

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        NORMAL(0, "正常"),
        STOP(1, "停用"),
        BAN(2, "封禁");
        public final Integer code;
        public final String desc;
    }

    @Getter
    @RequiredArgsConstructor
    public enum LoginType {
        ACCOUNT_PASSWORD(0, "账号密码"),
        PHONE_CODE(1, "手机验证码"),
        EMAIL_CODE(2, "邮箱验证码");
        public final Integer code;
        public final String desc;
    }


}
