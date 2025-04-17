package xyz.spc.serve.cluster.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.constant.ReqRespCT;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.domain.dos.Cluster.functions.NoticeDO;
import xyz.spc.domain.dos.Money.standards.CurrencyDO;
import xyz.spc.gate.dto.Cluster.clusters.ClusterDTO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterGreatVO;
import xyz.spc.gate.vo.Cluster.clusters.ClusterVO;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.infra.feign.Guest.UsersClient;
import xyz.spc.infra.feign.Money.StandardsClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.cluster.func.clusters.ClustersFunc;
import xyz.spc.serve.cluster.func.functions.NoticeFunc;
import xyz.spc.serve.cluster.func.interacts.RemarkFunc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClustersFlow {


    //Feign
    private final UsersClient usersClient;
    private final StandardsClient standardsClient;


    //Func
    private final ClustersFunc clustersFunc;
    private final NoticeFunc noticeFunc;
    private final RemarkFunc remarkFunc;


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
        return clusterList.stream().map(cluster -> {

            ClusterVO clusterVO = new ClusterVO();

            clusterVO.setId(cluster.getId());
            clusterVO.setName(cluster.getName());
            clusterVO.setNickname(cluster.getNickname());
            clusterVO.setPic(cluster.getPic());
            clusterVO.setPopVolume(cluster.getPopVolume());
            clusterVO.setCreateTime(cluster.getCreateTime());
            clusterVO.setUpdateTime(cluster.getUpdateTime());

            return clusterVO;
        }).toList();

    }

    /**
     * 直接分页查询所有群组 (简单VO 列表, 用于展示数据和进一步的入口)
     */
    public PageResponse<ClusterVO> getHallClusters(PageRequest pageRequest) {
        List<ClusterVO> tmp = clustersFunc.getHallClusters(pageRequest.getCurrent(), pageRequest.getSize());

        if (tmp == null || tmp.isEmpty()) {
            return new PageResponse<>(pageRequest.getCurrent(), pageRequest.getSize(), 0, null);
        }

        PageResponse<ClusterVO> res = new PageResponse<>(
                pageRequest.getCurrent(),
                pageRequest.getSize(),
                tmp.size(),
                tmp
        );

        return res;
    }

    /**
     * 用户id来进行分页查询群组 (简单VO 列表, 用于展示数据和进一步的入口)
     */
    public PageResponse<ClusterVO> getYardClusters(PageRequest pageRequest) {


        Long userId = Objects.requireNonNull(UserContext.getUI());

        // 获取用户加入的群组清单
        Result<List<Long>> result = Optional.ofNullable(usersClient.getUserClusterIds()).orElseThrow();
        List<Long> clusterIds = result.getData();

        // 批量查询群组基本存储对象信息
        List<ClusterDO> clusterList = clustersFunc.getClusterByIdBatch(clusterIds);

        // 手动处理分页返回 (处理类型组装分页返回值)
        int currentPage = pageRequest.getCurrent();
        int pageSize = pageRequest.getSize();
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, clusterList.size());

        // 避免越界
        if (fromIndex >= clusterList.size()) {
            return new PageResponse<>(currentPage, pageSize, clusterList.size(), List.of());
        }

        // 获取当前页的 ClusterDO
        List<ClusterDO> pagedClusterList = clusterList.subList(fromIndex, toIndex);


        // 将 ClusterDO 转换为 ClusterVO
        List<ClusterVO> pagedClusterVOList = pagedClusterList.stream().map(cluster -> {
            ClusterVO clusterVO = new ClusterVO();
            clusterVO.setId(cluster.getId());
            clusterVO.setName(cluster.getName());
            clusterVO.setNickname(cluster.getNickname());
            clusterVO.setPic(cluster.getPic());
            clusterVO.setPopVolume(cluster.getPopVolume());
            clusterVO.setCreateTime(cluster.getCreateTime());
            clusterVO.setUpdateTime(cluster.getUpdateTime());
            return clusterVO;
        }).toList();


        // 构建分页响应
        PageResponse<ClusterVO> res = new PageResponse<>(
                pageRequest.getCurrent(),
                pageRequest.getSize(),
                clusterList.size(),
                pagedClusterVOList
        );

        return res;

    }


    /**
     * 联表查询群组信息
     */
    public ClusterGreatVO getAllClusterById(Long id) {

        //1 联表查询三张表
        ClusterGreatVO clusterGreatVO = clustersFunc.getClusterInfo(id);

        //2 补充字段Tmodel


        //? 2.0 补充 UserDO

        String userAccount = "";
        Integer userisAdmin = 0;
        // 直接查 GreatVO 里面的 userId找到对应的 UserDO 即可
        //? 一般RPC, 直接用Result 包一层是推荐的, 因为可以被复用到前端 (不考虑性能) 这种写法可以, 下面的也可以
        Result<UserVO> res1 = usersClient.getUserDOInfo(clusterGreatVO.getCreatorUserId());
        if (Objects.equals(res1.getCode(), ReqRespCT.FAIL_CODE)) {
            throw new ServiceException(ServerError.SERVICE_RPC_ERROR);
        }


        userAccount = res1.getData().getAccount();
        userisAdmin = res1.getData().getAdmin();


        //? 2.1 补充 NoticeDO

        String noticeName = "";
        String noticeContent = "";
        // 直接查 群组功能 Notice by id
        NoticeDO noticeById = Optional.ofNullable(noticeFunc.getNoticeById(clusterGreatVO.getNoticeId())).orElse(
                NoticeDO.builder()
                        .name("没有公告哦")
                        .content("这里什么都么有")
                        .build()
        );

        noticeName = noticeById.getName();
        noticeContent = noticeById.getContent();


        //? 2.2 补充 CurrencyDO

        String currencyName = "";
        Float currencyExchangeRate = 0f;
        String currencyPic = "";

        // 直接查 Money模块 的 CurrencyDO 信息 by id
        CurrencyDO currencyById = standardsClient.getCurrencyById(clusterGreatVO.getCurrencyId()).getData();

        currencyName = currencyById.getName();
        currencyExchangeRate = currencyById.getExchangeRate();
        currencyPic = currencyById.getPic();

        //? 2.3 补充 RemarkDOS


        //! 查询群组的评论, 默认展示高赞的三条, 组装为List, 按照赞数降序排序.
        // 直接查 群组互动 RemarkDO by clusterid + type == 0

        List<String> content = remarkFunc.getRemark4ClusterHallShow(
                clusterGreatVO.getId(),
                3
        );

        //3 组装返回
        clusterGreatVO.setUserAccount(userAccount);
        clusterGreatVO.setUserisAdmin(userisAdmin);
        clusterGreatVO.setNoticeName(noticeName);
        clusterGreatVO.setNoticeContent(noticeContent);
        clusterGreatVO.setCurrencyName(currencyName);
        clusterGreatVO.setCurrencyExchangeRate(currencyExchangeRate);
        clusterGreatVO.setCurrencyPic(currencyPic);
        clusterGreatVO.setContent(content);

        return clusterGreatVO;
    }


    /**
     * 创建群组, 三张表插入
     */
    @Transactional(rollbackFor = Exception.class)
    public void createCluster(ClusterDTO clusterDTO) {

        //1. 创建群组
        Long clusterId = clustersFunc.createCluster(clusterDTO);

        //2. 把群主加到群里面 (Feign 不保证调用成功, 后面出错了需要用消息来进行补偿尝试 todo)
        usersClient.creatorJoinCluster(clusterId);
    }


    /**
     * 删除 (解散) 群组 - 逻辑修改对应字段
     */
    public void deleteCluster(Long clusterId) {

        // 鉴权判断: 只有群主可以删除本群组, 其他不行
        if (!clustersFunc.checkClusterCreatorEqual(clusterId)) {
            throw new ClientException("你不是群主, 无法解散本群组");
        }

        //1. 逻辑修改群组表
        clustersFunc.deleteCluster(clusterId);


        //2. 退群 = 删除 (这里是删除!) 群组用户关联 的 对应条目记录 (这个因为没有什么必要保存 (中间表), 所以简化了)
        usersClient.quitCluster(clusterId);

        //(3. 如果群组自定义了货币, 可以选择使用定时任务将其进行逻辑删除下线 (不可直接删除, 因为有用户没使用完)
        // -> 注册定时任务事件, 隔一段时间进行扫描无人使用的删除群组的货币, 将其逻辑下线掉.


        //4. 删除群组后, 所有人 都需要退出
        usersClient.everyQuitCluster(clusterId);
    }

    /**
     * 普通群成员退出群组
     */
    public void exitCluster(Long clusterId) {

        //1. 鉴权判断: 群主不可以退出自己的群组, 其他人可以!
        if (clustersFunc.checkClusterCreatorEqual(clusterId)) {
            throw new ClientException("群主不可以退出自己的群组");
        }

        //2. 退出操作, 删除关联
        usersClient.quitCluster(clusterId);

        //3. 交由对方服务实现计数维护.
    }


    /**
     * 将目标踢出某群组
     */
    public void kickCluster(Long clusterId, Long userId) {

        //1. 鉴权判断: 群主不可以退出他对应的群组, 其他人可以!
        if (clustersFunc.checkClusterCreatorEqual(clusterId, userId)) {
            throw new ClientException("群主不可以退出自己的群组");
        }

        //2. 退出操作, 删除关联
        Result<Object> res = usersClient.kickOutPopOfCluster(clusterId, userId);

        //3. 交由对方服务实现计数维护.

        //4. 需要处理失败情况
        if (Objects.equals(res.getCode(), ReqRespCT.FAIL_CODE)) {
            throw new ClientException(res.getMessage());
        }
    }

    /**
     * 加入群组 创建对应关系
     */
    public void joinHallCluster(Long clusterId) {

        //? Feign 调用情况下, 涉及到对方远程接口的抛异常回滚问题 (分布式事务) 简单解决: 一对一模式下, 直接接受返回值进行判断. 失败了就手动同步抛异常
        //? 如果是复杂的嵌套调用, 会使用文档里面的已经设计好的 延迟一致性方案 (消息队列), 之后会有特定的场景处理这个问题


        Result<Object> res = usersClient.joinCluster(clusterId);

        // 需要处理失败情况
        if (Objects.equals(res.getCode(), ReqRespCT.FAIL_CODE)) {
            throw new ClientException(res.getMessage());
        }
    }

    /**
     * 获取我加入的群组简单信息清单
     */
    public List<ClusterVO> getClusterEzOfMe() {

        //1. 获取用户加入的群组清单
        List<Long> clusterIds = null;
        Result<List<Long>> res = usersClient.getUserClusterIds();

        if (Objects.equals(res.getCode(), ReqRespCT.SUCCESS_CODE)) {
            clusterIds = res.getData();
        }

        if (clusterIds == null || clusterIds.isEmpty()) {
            return List.of();
        }

        //2. 批量查询群组基本存储对象信息
        return clustersFunc.getClusterEzOfMe(clusterIds);
    }


    /**
     * 判断群组创建者是否是这个用户
     */
    public boolean checkClusterCreatorEqual(Long clusterId, Long myUserId) {
        return clustersFunc.checkClusterCreatorEqual(clusterId, myUserId);
    }


    /**
     * 判断对应群组人满了没有
     */
    public boolean checkClusterFull(Long clusterId) {

        // 1 获得对应群组人数上限字段
        int maxUserCount = clustersFunc.getClusterMaxUserCount(clusterId);

        // 2 获取群组当前人数 - 无字段需要手动加载计算
        int currentUserCount = this.getClusterUserCount(clusterId);

        // 3 判断
        return currentUserCount >= maxUserCount;

    }


    /**
     * 获取对应群组人员数量
     */
    public int getClusterUserCount(Long clusterId) {
        Result<Integer> r = usersClient.getClusterUserCount(clusterId);
        int i = r.getData();

        return i;
    }


    /**
     * 操作对应群组人数记录字段 操作类型 + 对应数量
     * ? note 这个方法是空的, 因为暂时没有设计对应的 字段 存储人数, 都是用 Tmodel, 这个留给后面
     */
    public void opClusterUserCount(Long clusterId, String opType, int amount) {

        // 查出对应记录

        // 操作
    }


}
