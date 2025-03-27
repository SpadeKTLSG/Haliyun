package xyz.spc.infra.mapper.Guest.users;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Guest.users.UserClusterDO;

@Mapper
public interface UserClusterMapper extends MPJBaseMapper<UserClusterDO> {
}
