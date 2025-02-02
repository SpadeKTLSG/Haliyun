package xyz.spc.serve.guest.func.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.infra.repo.Guest.users.UserService;
import xyz.spc.infra.repo.Guest.users.UsersRepo;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsersFuncImpl implements UsersFunc {


    //...
    public final UserService userService;
    private final UsersRepo usersRepo; //利用Repo来聚合Service

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
        User userModel = User.builder().id(114514L).build();
        UserDO userDO = userModel.toDO();
        userService.getById(userDO);
//        userDO = usersRepo.userService.getById(userDO);
        System.out.println("get! ");
        return null;
    }

}
