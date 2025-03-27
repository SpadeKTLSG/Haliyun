package xyz.spc.serve.group.func.groups;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.groups.ClusterDO;
import xyz.spc.infra.special.Cluster.groups.ClustersRepo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClustersFunc {

    /**
     * Repo
     */
    private final ClustersRepo groupsRepo;

    /**
     * 根据groupIds获取groupNames
     */
    public List<String> getClusterNamesByIds(List<Long> groupIds) {

        List<String> res = new ArrayList<>();
        for (Long groupId : groupIds) {
            ClusterDO group = groupsRepo.groupService.getById(groupId);
            if (group != null) {
                res.add(group.getName());
            } else {
                log.warn("Cluster with id {} not found", groupId);
            }
        }
        return res;
    }
}
