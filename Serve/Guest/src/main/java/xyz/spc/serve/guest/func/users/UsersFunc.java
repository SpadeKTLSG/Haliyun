package xyz.spc.serve.guest.func.users;

import xyz.spc.gate.dto.Guest.users.UserDTO;

import javax.security.auth.login.AccountNotFoundException;

public interface UsersFunc {

    /**
     * 发送手机验证码
     */
    String sendCode(String phone);

    /**
     * 登录
     */
    String login(UserDTO userDTO) throws AccountNotFoundException;

    /**
     * 登出
     */
    Boolean logout();
}
