package xyz.spc.serve.cluster.func.clusters;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
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


    /**
     * 根据ids批量查询群组
     */
    public List<ClusterDO> getClusterByIdBatch(List<Long> pagedClusterIds) {

        if (pagedClusterIds == null || pagedClusterIds.isEmpty()) {
            return List.of();
        }

        return clustersRepo.clusterMapper.selectBatchIds(pagedClusterIds);
    }

    /**
     * MP内置分页查询
     */
    public List<ClusterVO> getHallClusters(Integer page, Integer size) {
        // 分页查询群组对象
        List<ClusterDO> clusterList = clustersRepo.clusterMapper.selectPage(
                new Page<>(page, size),
                null
        ).getRecords();

        if (clusterList == null || clusterList.isEmpty()) {
            return List.of();
        }

        // 补充信息
        return clusterList.stream()
                .map(cluster -> {
                    ClusterVO clusterVO = new ClusterVO();

                    clusterVO.setId(cluster.getId());
                    clusterVO.setName(cluster.getName());
                    clusterVO.setNickname(cluster.getNickname());
                    clusterVO.setPic(cluster.getPic());
                    clusterVO.setPopVolume(cluster.getPopVolume());
                    clusterVO.setCreateTime(cluster.getCreateTime());
                    clusterVO.setUpdateTime(cluster.getUpdateTime());

                    return clusterVO;
                })
                .toList();
    }


}
