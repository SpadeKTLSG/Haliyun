package xyz.spc.infra.repo.Group.groups;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Group.groups.GroupDetailDO;
import xyz.spc.infra.mapper.Group.groups.GroupDetailMapper;

@Service
@RequiredArgsConstructor
public class GroupDetailServiceImpl extends ServiceImpl<GroupDetailMapper, GroupDetailDO> implements GroupDetailService {
}
