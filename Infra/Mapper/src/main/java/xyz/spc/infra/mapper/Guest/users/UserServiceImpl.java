package xyz.spc.infra.mapper.Guest.users;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dao.Guest.users.UserDAO;
import xyz.spc.domain.dos.Guest.users.UserDO;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final UserDAO userDAO;

    @Bean
    @Override
    public UserDAO userDAO() {
        return userDAO;
    }
}
