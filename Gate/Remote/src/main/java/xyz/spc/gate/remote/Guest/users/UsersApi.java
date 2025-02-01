package xyz.spc.gate.remote.Guest.users;

import xyz.spc.common.funcpack.commu.Result;
import xyz.spc.gate.vo.Guest.users.UserVO;

public interface UsersApi {


    //test CRUD of Users

    void add();


    void delete();


    void update();


    Result<UserVO> get();


}
