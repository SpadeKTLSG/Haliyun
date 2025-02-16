package xyz.spc.infra.repo.Group.managers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.managers.GroupAuthDO;
import xyz.spc.infra.mapper.Group.managers.GroupAuthMapper;

@Service
@RequiredArgsConstructor
public class GroupAuthServiceImpl extends ServiceImpl<GroupAuthMapper, GroupAuthDO> implements GroupAuthService {
}
