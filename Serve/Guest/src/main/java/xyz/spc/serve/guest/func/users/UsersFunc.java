package xyz.spc.serve.guest.func.users;

import xyz.spc.gate.vo.Guest.users.UserVO;

public interface UsersFunc {

    void add();

    void delete();

    void update();

    UserVO get();
}
