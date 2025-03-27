package xyz.spc.infra.repo.Guest.users;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserClusterDO;
import xyz.spc.infra.mapper.Guest.users.UserClusterMapper;

@Service
@RequiredArgsConstructor
public class UserClusterServiceImpl extends ServiceImpl<UserClusterMapper, UserClusterDO> implements UserClusterService {
}
