package xyz.spc.serve.cluster.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.cluster.func.clusters.ClustersFunc;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClustersFlow {


    //Feign
    private final ClustersClient clustersClient;

    //Func
    private final ClustersFunc clustersFunc;


    public List<String> getClusterNamesByIds(List<Long> groupIds) {
        return clustersFunc.getClusterNamesByIds(groupIds);
    }


    /**
     * 根据id批量查询群组
     */
    public List<ClusterVO> getClusterByIdBatch(List<Long> pagedClusterIds) {
        // 批量查询群组基本存储对象
        List<ClusterDO> clusterList = clustersFunc.getClusterByIdBatch(pagedClusterIds);

        if (clusterList == null || clusterList.isEmpty()) {
            return List.of();
        }

        // 群组暂无补充信息


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

    /**
     * 直接分页查询所有群组 (简单VO 列表, 用于展示数据和进一步的入口)
     */
    public List<ClusterVO> getHallClusters(Integer page, Integer size) {
        return clustersFunc.getHallClusters(page, size);
    }

    /**
     * 用户id来进行分页查询群组 (简单VO 列表, 用于展示数据和进一步的入口)
     */
    public List<ClusterVO> getYardClusters(Integer page, Integer size) {
        // 1. 获取用户加入的群组清单
        Long userId = Objects.requireNonNull(UserContext.getUI());


        // 2. 批量查询群组基本存储对象
        return clustersFunc.getYardClusters(page, size);
    }
}
