package xyz.spc.serve.guest.func.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.domain.model.Guest.users.User;
import xyz.spc.domain.repo.Guest.users.UsersRepo;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.infra.mapper.Guest.users.UserService;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsersFuncImpl implements UsersFunc {


    private final UsersRepo usersRepo; //利用Repo来聚合Service
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
        User userModel = User.builder().id(114514L).build();
        UserDO userDO = userModel.toDO();
        UserService temp = usersRepo.getUserService();
        temp.getById(userDO);
//        userDO = usersRepo.userService.getById(userDO);
        System.out.println("get! ");
        return null;
    }

}
