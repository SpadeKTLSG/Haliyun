package xyz.spc.serve.guest.func.users;

import jakarta.servlet.http.HttpSession;
import xyz.spc.gate.vo.Guest.users.UserVO;

public interface UsersFunc {

    void add();

    void delete();

    void update();

    UserVO get();

    /**
     * 发送手机验证码
     */
    String sendCode(String phone, HttpSession session);
}
