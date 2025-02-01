package xyz.spc.serve.guest.func.users;

import lombok.RequiredArgsConstructor;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.infra.mapper.Guest.users.UserService;

@RequiredArgsConstructor
public class UsersFuncImpl implements UsersFunc {

    private final UserService userService;
    //...


    @Override
    public void add() {
        System.out.println("add");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public UserVO get() {
        //这里要用User -> UserDO 转换. 执行之后再转回来
        userService.getOne();
        System.out.println("get");
        return null;
    }

}
