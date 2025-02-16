package xyz.spc.infra.repo.Group.functions;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.functions.GroupLevelPrefixDO;
import xyz.spc.infra.mapper.Group.functions.GroupLevelPrefixMapper;

@Service
@RequiredArgsConstructor
public class GroupLevelPrefixServiceImpl extends ServiceImpl<GroupLevelPrefixMapper, GroupLevelPrefixDO> implements GroupLevelPrefixService {
}
