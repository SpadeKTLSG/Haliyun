package xyz.spc.serve.cluster.func.clusters;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.picture.PictureCT;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDetailDO;
import xyz.spc.domain.dos.Cluster.clusters.ClusterFuncDO;
import xyz.spc.domain.model.Cluster.clusters.Cluster;
import xyz.spc.gate.dto.Cluster.clusters.ClusterDTO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterGreatVO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
import xyz.spc.infra.special.Cluster.clusters.ClustersRepo;
import xyz.spc.serve.auxiliary.common.context.UserContext;

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


    public ClusterGreatVO getClusterInfo(Long id) {

        return clustersRepo.clusterMapper.selectJoinOne(ClusterGreatVO.class, new MPJLambdaWrapper<ClusterDO>()
                .selectAll(ClusterDO.class)
                .selectAll(ClusterDetailDO.class)
                .selectAll(ClusterFuncDO.class)
                .leftJoin(ClusterDetailDO.class, ClusterDetailDO::getId, ClusterDO::getId)
                .leftJoin(ClusterFuncDO.class, ClusterFuncDO::getId, ClusterDO::getId)
                .eq(ClusterDO::getId, id)
        );

    }

    /**
     * 添加群组, 插入三张表
     */
    public void createCluster(ClusterDTO clusterDTO) {

        //基本没有校验限制, 确认前端nickname 和name非空, 其他空.
        String name = clusterDTO.getName();
        String nickname = clusterDTO.getNickname();

        // 插入三张表, 生成对应的统一ID
        Long id = SnowflakeIdUtil.nextId();

        //? 插入 ClusterDO
        ClusterDO clusterDO = ClusterDO.builder()
                .id(id)
                .creatorUserId(UserContext.getUI())
                .name(name)
                .nickname(nickname)
                .pic(PictureCT.DEFAULT_PIC)
                .popVolume(Cluster.BASIC_POP_VOLUME) // VIP 后续手动触发群组容量提升, 这里一视同仁
                .build();
        clustersRepo.clusterMapper.insert(clusterDO);


        //? 插入 ClusterDetailDO
        ClusterDetailDO clusterDetailDO = ClusterDetailDO.builder()
                .id(id)
                .build();
        clustersRepo.clusterDetailMapper.insert(clusterDetailDO);


        //? 插入 ClusterFuncDO
        ClusterFuncDO clusterFuncDO = ClusterFuncDO.builder()
                .id(id)
                .build();
        clustersRepo.clusterFuncMapper.insert(clusterFuncDO);

    }
}
