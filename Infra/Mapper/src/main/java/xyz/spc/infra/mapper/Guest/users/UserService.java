package xyz.spc.infra.mapper.Guest.users;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.context.annotation.Bean;
import xyz.spc.domain.dao.Guest.users.UserDAO;
import xyz.spc.domain.dos.Guest.users.UserDO;

public interface UserService extends IService<UserDO> {


    @Bean
    UserDAO userDAO();
}
