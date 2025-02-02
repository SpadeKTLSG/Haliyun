package xyz.spc.infra.repo.Guest.users;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserDO;
import xyz.spc.infra.mapper.Guest.users.UserMapper;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
}
