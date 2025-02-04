package xyz.spc.gate.dto.Guest.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.common.funcpack.validate.Guest.UsersValiGroups;
import xyz.spc.gate.dto.BaseDTO;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {
    
    private Long id;

    @NotNull(groups = {UsersValiGroups.Login.class}, message = "登陆用户类型不能为空")
    private Integer admin;

    @NotNull(groups = {UsersValiGroups.Login.class}, message = "登陆用户状态不能为空")
    private Integer status;

    @NotNull(groups = {UsersValiGroups.Login.class}, message = "登陆方式不能为空")
    private Integer loginType;

    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "登陆账号不能为空")
    private String account;

    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "登陆密码不能为空")
    private String password;

    /**
     * 手机号
     */
    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "登陆手机号不能为空")
    private String phone;

    /**
     * 验证码
     */
    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "登陆验证码不能为空")
    private String code;

    /**
     * Token
     */
    private String token;
}
