package xyz.spc.serve.cluster.func.clusters;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.infra.special.Cluster.clusters.ClustersRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        if (clusterIds == null || clusterIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 批量查询群组对象
        List<ClusterDO> clusters = clustersRepo.clusterMapper.selectBatchIds(clusterIds);

        // 提取群组名称
        return clusters.stream()
                .map(ClusterDO::getName)
                .collect(Collectors.toList());
    }
}
