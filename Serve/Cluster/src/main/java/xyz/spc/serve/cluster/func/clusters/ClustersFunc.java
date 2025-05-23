package xyz.spc.serve.cluster.func.clusters;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.constant.DelEnum;
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

        return clustersRepo.clusterMapper.selectList(Wrappers.lambdaQuery(ClusterDO.class)
                .eq(ClusterDO::getDelFlag, DelEnum.NORMAL.getStatusCode()) // 逻辑删除处理
                .in(ClusterDO::getId, pagedClusterIds) // 批量查询
                .orderByDesc(ClusterDO::getCreateTime) //从新到旧排序
        );
    }

    /**
     * MP内置分页查询
     */
    public List<ClusterVO> getHallClusters(Integer page, Integer size) {
        // 分页查询群组对象
        List<ClusterDO> clusterList = clustersRepo.clusterMapper.selectPage(
                new Page<>(page, size),
                Wrappers.lambdaQuery(ClusterDO.class)
                        .eq(ClusterDO::getDelFlag, DelEnum.NORMAL.getStatusCode()) // 逻辑删除处理
                        .orderByDesc(ClusterDO::getCreateTime) //从新到旧排序
        ).getRecords();

        if (clusterList == null || clusterList.isEmpty()) {
            return List.of();
        }

        // 补充信息
        return clusterList.stream()
                .map(cluster -> {
                    ClusterVO clusterVO = new ClusterVO();

                    clusterVO.setId(String.valueOf(cluster.getId()));
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
    public Long createCluster(ClusterDTO clusterDTO) {

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
        return id;
    }

    /**
     * 逻辑删除群组修改del字段
     */
    public void deleteCluster(Long clusterId) {

        // 逻辑删除群组表
        clustersRepo.clusterService.update(
                null,
                Wrappers.lambdaUpdate(ClusterDO.class)
                        .eq(ClusterDO::getId, clusterId)
                        .set(ClusterDO::getDelFlag, DelEnum.DELETE.getStatusCode())
        );

        // 逻辑删除群组详情表
        clustersRepo.clusterDetailService.update(
                null,
                Wrappers.lambdaUpdate(ClusterDetailDO.class)
                        .eq(ClusterDetailDO::getId, clusterId)
                        .set(ClusterDetailDO::getDelFlag, DelEnum.DELETE.getStatusCode())
        );

        // 逻辑删除群组功能表
        clustersRepo.clusterFuncService.update(
                null,
                Wrappers.lambdaUpdate(ClusterFuncDO.class)
                        .eq(ClusterFuncDO::getId, clusterId)
                        .set(ClusterFuncDO::getDelFlag, DelEnum.DELETE.getStatusCode())
        );

        log.debug("群组: {} , 由用户: {} 删除成功: ", clusterId, UserContext.getUA());
    }


    /**
     * 检查群组id对应的创建者是否是当前用户
     */
    public boolean checkClusterCreatorEqual(Long clusterId) {
        return checkClusterCreatorEqual(clusterId, UserContext.getUI());
    }

    /**
     * 检查群组id对应的创建者是否是目标用户
     */
    public boolean checkClusterCreatorEqual(Long clusterId, Long userId) {

        //1. 查出对应的群组 ClusterDO
        ClusterDO clusterDO = clustersRepo.clusterMapper.selectOne(
                Wrappers.lambdaQuery(ClusterDO.class)
                        .eq(ClusterDO::getId, clusterId)
                        .eq(ClusterDO::getDelFlag, DelEnum.NORMAL.getStatusCode()) // 逻辑删除处理
        );

        //2. 获取群组创建人id
        Long creatorUserId = clusterDO.getCreatorUserId();

        //3. 判断是否和目标对象相等
        if (creatorUserId.equals(userId)) {
            return true;
        } else {
            log.error("用户: {} 试图删除群组 => {}, 但不是群主, 无法删除", userId, clusterId);
            return false;
        }
    }


    /**
     * 获取我加入的群组信息, 用于群组信息页面
     */
    public List<ClusterVO> getClusterEzOfMe(List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        // 批量查询群组对象
        List<ClusterDO> clusterList = clustersRepo.clusterMapper.selectBatchIds(ids);

        if (clusterList == null || clusterList.isEmpty()) {
            return List.of();
        }

        // 补充信息
        return clusterList.stream()
                .map(cluster -> {
                    ClusterVO clusterVO = new ClusterVO();

                    // 需要查出基本信息: id, 名称, 人员容量 用于展示
                    clusterVO.setId(String.valueOf(cluster.getId()));
                    clusterVO.setName(cluster.getName());
                    clusterVO.setPopVolume(cluster.getPopVolume());
                    clusterVO.setCreatorUserId(String.valueOf(cluster.getCreatorUserId())); // 必须补充群主id, 用于操作鉴权等场景
                    // note: 部分安全性低场景前端短路实现可以使用前端直接把这个群主id拿去判断Context中的userId是否相等, 这样就不需要再查数据库了
                    // 但是不推荐, 最后还是要在后端做保护, 以免被人恶意篡改. 实现的对应见 两处: Notice模块 + 群组信息模块

                    return clusterVO;
                })
                .toList();
    }

    /**
     * 用群组id获取群组公告id
     */
    public Long getNoticeIdByClusterId(Long clusterId) {

        // 通过群组id 找到 ClusterFuncDO -> 公告id
        ClusterFuncDO clusterFuncDO = clustersRepo.clusterFuncService.getOne(
                Wrappers.lambdaQuery(ClusterFuncDO.class)
                        .eq(ClusterFuncDO::getId, clusterId)
                        .eq(ClusterFuncDO::getDelFlag, DelEnum.NORMAL.getStatusCode()) // 逻辑删除处理
        );

        Long noticeId = clusterFuncDO.getNoticeId();

        return noticeId;
    }

    /**
     * 更新群组公告id
     */
    public void updateNoticeId(Long clusterId, Long noticeId) {

        // 更新群组公告id
        clustersRepo.clusterFuncService.update(
                null,
                Wrappers.lambdaUpdate(ClusterFuncDO.class)
                        .eq(ClusterFuncDO::getId, clusterId)
                        .eq(ClusterFuncDO::getDelFlag, DelEnum.NORMAL.getStatusCode()) // 逻辑删除处理
                        .set(ClusterFuncDO::getNoticeId, noticeId)
        );

    }

    /**
     * 获取群组的最大人数字段
     */
    public int getClusterMaxUserCount(Long clusterId) {

        ClusterDO one = clustersRepo.clusterService.getOne(
                Wrappers.lambdaQuery(ClusterDO.class)
                        .eq(ClusterDO::getId, clusterId)
                        .eq(ClusterDO::getDelFlag, DelEnum.NORMAL.getStatusCode())
        );

        return one.getPopVolume();
    }



}
