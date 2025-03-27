package xyz.spc.serve.cluster.func.clusters;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.infra.special.Cluster.clusters.ClustersRepo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClustersFunc {

    /**
     * Repo
     */
    private final ClustersRepo clustersRepo;

    /**
     * 根据groupIds获取groupNames
     */
    public List<String> getClusterNamesByIds(List<Long> clusterIds) {

        List<String> res = new ArrayList<>();
        for (Long clusterId : clusterIds) {
            ClusterDO cluster = clustersRepo.clusterService.getById(clusterId);
            if (cluster != null) {
                res.add(cluster.getName());
            } else {
                log.warn("Cluster with id {} not found", clusterId);
            }
        }
        return res;
    }
}
