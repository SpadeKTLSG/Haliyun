package xyz.spc.infra.mapper.Guest.users;

import com.github.yulichang.base.MPJBaseMapper;
import xyz.spc.domain.dao.Guest.users.UserDAO;
import xyz.spc.domain.model.Guest.users.User;

public interface UserMapper extends MPJBaseMapper<User>, UserDAO {
}
