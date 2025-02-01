package xyz.spc.domain.qo.Guest.users;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.spc.domain.qo.BaseQO;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserResp extends BaseQO {


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
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;
}
