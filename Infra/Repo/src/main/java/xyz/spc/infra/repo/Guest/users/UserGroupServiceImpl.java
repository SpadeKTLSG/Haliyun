package xyz.spc.infra.repo.Guest.users;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.users.UserGroupDO;
import xyz.spc.infra.mapper.Guest.users.UserGroupMapper;

@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroupDO> implements UserGroupService {
}
