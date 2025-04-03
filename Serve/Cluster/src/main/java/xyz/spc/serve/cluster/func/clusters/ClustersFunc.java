package xyz.spc.serve.cluster.func.clusters;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.picture.PictureCT;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.common.util.collecUtil.JsonUtil;
import xyz.spc.common.util.mathUtil.UnitUtil;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDetailDO;
import xyz.spc.domain.dos.Cluster.clusters.ClusterFuncDO;
import xyz.spc.domain.model.Cluster.clusters.Cluster;
import xyz.spc.domain.model.Cluster.clusters.ClusterDetail;
import xyz.spc.domain.model.Cluster.clusters.ClusterFunc;
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

        clustersRepo.clusterService.save(clusterDO);


        //? 插入 ClusterDetailDO
        ClusterDetailDO clusterDetailDO = ClusterDetailDO.builder()
                .id(id)
                .shareLink(
                        UserContext.getUI() + "$" + id
                ) // 群组分享的唯一链接设置为 (群组不可转让) 创建者用户 id + 群组 id, 用 $ 分隔
                .album(
                        JsonUtil.getInstance().toJson( // ? 使用自制 JSON 序列化工具来存储, 之后都这么干, Tom!
                                List.of(PictureCT.DEFAULT_PIC)
                        )// 默认相册用一张默认图片来存储, 其他的图片后续手动添加

                )// 相册用JSON来存储对应的String, 每个都是一张照片的链接
                .usedSpace(0L) // 已使用空间为0
                .totalSpace(UnitUtil.toGBSize4Long(ClusterDetail.BASIC_TOTAL_SPACE)) // 默认用户总空间 (转化为GB大小)
                .build();

        clustersRepo.clusterDetailService.save(clusterDetailDO);


        //? 插入 ClusterFuncDO
        ClusterFuncDO clusterFuncDO = ClusterFuncDO.builder()
                .id(id)
                .noticeId(ClusterFunc.DEFAULT_NOTICE_ID) // 默认公告ID
                .currencyId(ClusterFunc.DEFAULT_CURRENCY_ID) // 默认货币ID
                .allowInvite(ClusterFunc.ALLOW_INVITE_ALLOW) // 默认允许邀请
                .currencyStock(ClusterFunc.DEFAULT_CURRENCY_STOCK) // 默认货币储备
                .coinStock(ClusterFunc.DEFAULT_COIN_STOCK)  // 默认硬币储备
                .remark(ClusterFunc.DEFAULT_REMARK) // 默认评论(类似备注, 是群主写的, 不是另一个RemarkDO )
                .build();

        clustersRepo.clusterFuncService.save(clusterFuncDO);

        log.debug("群组: {} , 由用户: {} 注册成功: ", name, UserContext.getUA());

    }
}
