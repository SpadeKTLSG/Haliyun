package xyz.spc.serve.guest.func.users;

import jakarta.servlet.http.HttpSession;
import xyz.spc.gate.dto.Guest.users.UserDTO;

public interface UsersFunc {

    /**
     * 发送手机验证码
     */
    String sendCode(String phone, HttpSession session);

    /**
     * 登录
     */
    String login(UserDTO userDTO, HttpSession session);
}
