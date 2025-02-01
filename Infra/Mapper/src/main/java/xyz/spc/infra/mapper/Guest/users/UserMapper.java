package xyz.spc.infra.mapper.Guest.users;

import com.github.yulichang.base.MPJBaseMapper;
import xyz.spc.domain.dao.Guest.users.UserDAO;
import xyz.spc.domain.dos.Guest.users.UserDO;

public interface UserMapper extends MPJBaseMapper<UserDO>, UserDAO {
}
