package xyz.spc.infra.repo.Group.groups;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.groups.GroupFuncDO;
import xyz.spc.infra.mapper.Group.groups.GroupFuncMapper;

@Service
@RequiredArgsConstructor
public class GroupFuncServiceImpl extends ServiceImpl<GroupFuncMapper, GroupFuncDO> implements GroupFuncService {
}
