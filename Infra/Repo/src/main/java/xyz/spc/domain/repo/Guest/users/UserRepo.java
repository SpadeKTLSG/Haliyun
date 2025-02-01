package xyz.spc.domain.repo.Guest.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.spc.infra.mapper.Guest.users.UserDetailMapper;
import xyz.spc.infra.mapper.Guest.users.UserMapper;

@Repository
@RequiredArgsConstructor
public class UserRepo {

    private final UserMapper userMapper;
    private final UserDetailMapper userDetailMapper;


    /**
     * 模拟编排DAO层的方法
     */
    public void test() {
        userMapper.test();
        userDetailMapper.test();
    }


}
