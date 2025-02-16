package xyz.spc.infra.special.Group.groups;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.groups.GroupDetailMapper;
import xyz.spc.infra.mapper.Group.groups.GroupFuncMapper;
import xyz.spc.infra.mapper.Group.groups.GroupMapper;
import xyz.spc.infra.repo.Group.groups.GroupDetailService;
import xyz.spc.infra.repo.Group.groups.GroupFuncService;
import xyz.spc.infra.repo.Group.groups.GroupService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class GroupsRepo {

    public final GroupService groupService;
    public final GroupMapper groupMapper;
    public final GroupDetailService groupDetailService;
    public final GroupDetailMapper groupDetailMapper;
    public final GroupFuncService groupFuncService;
    public final GroupFuncMapper groupFuncMapper;

}
