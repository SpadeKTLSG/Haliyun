package xyz.spc.serve.group.func.groups;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.special.Group.groups.GroupsRepo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupsFunc {

    /**
     * Repo
     */
    private final GroupsRepo groupsRepo;

    /**
     * 根据groupIds获取groupNames
     */
    public List<String> getGroupNamesByIds(List<Long> groupIds) {

        List<String> res = new ArrayList<>();
        for (Long groupId : groupIds) {
            res.add(groupsRepo.groupService.getById(groupId).getName());
        }
        return res;
    }
}
