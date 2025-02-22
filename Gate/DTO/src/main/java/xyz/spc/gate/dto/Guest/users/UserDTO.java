package xyz.spc.gate.dto.Guest.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.common.constant.Guest.UsersValiGroups;
import xyz.spc.common.funcpack.validate.NotChinese;
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
    @NotNull(groups = {UsersValiGroups.Register.class}, message = "注册用户类型不能为空")
    private Integer admin;

    private Integer status;

    @NotNull(groups = {UsersValiGroups.Login.class}, message = "登陆方式不能为空")
    private Integer loginType;

    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "登陆账号不能为空")
    @NotEmpty(groups = {UsersValiGroups.Register.class}, message = "注册账号名不能为空")
    private String account;

    @NotChinese(message = "密码不能包含中文")
//    @Pattern(groups = {UsersValiGroups.Common.class}, regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", message = "密码格式不正确, 需要6-16位数字字母组合")
    //改为前端判断(方便起见暂放开), 加密存储.
    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "登陆密码不能为空")
    @NotEmpty(groups = {UsersValiGroups.Register.class}, message = "注册密码不能为空")
    private String password;

    /**
     * 手机号
     */
    @Pattern(groups = {UsersValiGroups.Common.class}, regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "登陆手机号不能为空")
    @NotEmpty(groups = {UsersValiGroups.Register.class}, message = "注册手机号不能为空")
    private String phone;

    /**
     * 验证码
     */
    @NotEmpty(groups = {UsersValiGroups.Login.class}, message = "验证码不能为空")
    @NotEmpty(groups = {UsersValiGroups.Register.class}, message = "验证码不能为空")
    private String code;

    /**
     * Token
     */
    private String token;


    public enum UserDTOField {
        id, admin, status, loginType, account, password, phone, code, token
    }
}
