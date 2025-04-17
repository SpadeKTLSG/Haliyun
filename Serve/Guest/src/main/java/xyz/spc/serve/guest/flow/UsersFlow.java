package xyz.spc.serve.guest.flow;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.common.constant.SystemSpecialCT;
import xyz.spc.common.funcpack.Result;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.gate.vo.Guest.levels.LevelVO;
import xyz.spc.gate.vo.Guest.users.UserGreatVO;
import xyz.spc.gate.vo.Guest.users.UserVO;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.guest.func.levels.LevelFunc;
import xyz.spc.serve.guest.func.records.StatisticFunc;
import xyz.spc.serve.guest.func.records.TombFunc;
import xyz.spc.serve.guest.func.users.UserClusterFunc;
import xyz.spc.serve.guest.func.users.UsersFunc;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersFlow {

    //Feign
    private final ClustersClient clustersClient;

    //Func
    private final UsersFunc usersFunc;
    private final LevelFunc levelFunc;
    private final TombFunc tombFunc;
    private final StatisticFunc statisticFunc;
    private final UserClusterFunc userClusterFunc;


    /**
     * 发送验证码
     */
    public String sendCode(String phone) {
        return usersFunc.sendCodeCore(phone);
    }


    /**
     * 登陆
     */
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public String login(UserDTO userDTO) {
        return usersFunc.loginCore(userDTO); // 后续的登陆操作由前端发送请求再完成, 这里不继续推进后续了
    }

    /**
     * 登出
     */
    public boolean logout() {
        return usersFunc.logoutCore();
    }


    /**
     * 注册
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean register(UserDTO userDTO) {

        //1 注册用户核心表
        if (!usersFunc.registerCore(userDTO)) {
            return false;
        }

        //2 (异步) 注册统计表
        statisticFunc.registerStatistics(userDTO.getId());

        //3 (异步) 注册坟墓表
        tombFunc.registerTomb(userDTO.getId());

        return true;
    }

    /**
     * 获取用户标记
     */
    public Map<String, String> getUserMark(String account) {
        return usersFunc.getUserMark(account);
    }

    /**
     * 获取用户信息
     */
    public UserGreatVO getUserInfoWithClusters(Long id) {
        //获得用户基础联表三张信息
        UserGreatVO userGreatVO = usersFunc.getUserInfo(id);

        //补充查询基础信息: Level等级 => 名称和层级
        LevelVO levelVO = levelFunc.getLevelInfo(userGreatVO.getLevelId());
        userGreatVO.setLevelName(levelVO.getName());
        userGreatVO.setLevelFloor(levelVO.getFloor());


        //中间表查用户加入的群组ids集合
        List<Long> groupIds = userClusterFunc.getUsersClusterIds(id);

        //Cluster 模块Feign找对应群组名
        userGreatVO.setClusterNames(clustersClient.getClusterNamesByIds(groupIds));

        return userGreatVO;
    }

    /**
     * 查用户加入的群组ids集合 (清单)
     */
    public List<Long> getUserClusterIds() {

        Long userId = Objects.requireNonNull(UserContext.getUI());
        return userClusterFunc.getUsersClusterIds(userId);
    }


    /**
     * 更新用户信息
     */
    public void updateUserInfo(UserGreatVO userGreatVO) {
        //更新用户基础联表三张信息
        usersFunc.updateUserInfo(userGreatVO);
        //暂定不更新其他信息
    }

    /**
     * 注销账号
     */
    public void killUserAccount(Long id) {

        //群组: 退出所有群组, 解绑关系
        //todo

        //文件: 删除所有文件, 清理历史
        //todo


        //用户: 注销用户基础联表三张信息 - 逻辑删除
        usersFunc.killUserAccount(id);

        //用户记录: 注销用户记录表 * 2 - 逻辑删除
        statisticFunc.killUserAccountStatistics(id);
        tombFunc.killUserAccountTomb(id);

    }

    /**
     * 获取用户等级
     */
    public Integer getUserLevelFloor(Long id) {
        //获取对应的等级 id
        Long tmp = usersFunc.getUserLevelFloor(id);

        //查看对应id的等级的层级
        return levelFunc.getLevelInfo(tmp).getFloor();
    }

    /**
     * 获取用户信息
     */
    public UserVO getUserDOInfo(Long creatorUserId) {
        return usersFunc.getUserDOInfo(creatorUserId);
    }

    /**
     * 将当前 Context 的用户 (id) 加入群组 (id)
     */
    @Transactional(rollbackFor = Exception.class)
    public void joinCluster(Long clusterId) {

        Long userId = Objects.requireNonNull(UserContext.getUI());

        // 鉴权: 判断对应群组满了没...没满才可以加入
        Result<Object> r = clustersClient.checkClusterFull(clusterId);
        if ((boolean) r.getData()) {
            throw new ClientException("群组人数已满, 无法加入!!!");
        }

        // 鉴权: 判断当前用户是否已经加入了这个群组
        if (userClusterFunc.checkUserJoinCluster(userId, clusterId)) {
            throw new ClientException("你已经加入了这个群组, 无需重复加入");
        }

        // 加入群组表操作
        userClusterFunc.joinCluster(userId, clusterId);

        // 维护 UserFunc 的加入群组数量
        usersFunc.opUserJoinClusterCount(userId, SystemSpecialCT.ADD, 1);

        // 维护对应群组人员数量: +=1 (此方法目前无效果, 因为没有字段)
        clustersClient.opClusterUserCount(clusterId, SystemSpecialCT.ADD, 1);
    }

    /**
     * 将当前 Context 的用户 (id) 加入群组 (id), 但是是群主
     */
    public void creatorJoinCluster(Long clusterId) {

        Long userId = Objects.requireNonNull(UserContext.getUI());

        // 加入群组表操作
        userClusterFunc.joinCluster(userId, clusterId);

        // 维护 UserFunc 的创建群组数量
        usersFunc.opUserCreateClusterCount(userId, SystemSpecialCT.ADD, 1);

        // 维护 UserFunc 的加入群组数量
        usersFunc.opUserJoinClusterCount(userId, SystemSpecialCT.ADD, 1);

    }

    /**
     * 将当前 Context 的用户 (id) 退出群组 (id)
     */
    public void quitCluster(Long clusterId) {

        //退群 = 删除 (这里是删除!) 群组用户关联 的 对应条目记录 (这个因为没有什么必要保存 (中间表), 所以简化了)
        Long userId = Objects.requireNonNull(UserContext.getUI());

        //1. 群组表操作
        userClusterFunc.quitCluster(userId, clusterId);

        //2. 维护 UserFunc 的加入群组数量
        usersFunc.opUserJoinClusterCount(userId, SystemSpecialCT.SUB, 1);
    }

    /**
     * 将目标用户踢出某群组, 需要群主资格
     */
    public void kickOutPopOfCluster(Long clusterId, Long userId) {

        //1. 判断 myUserId 是否目标群组的群主
        Long myUserId = Objects.requireNonNull(UserContext.getUI());
        if (!clustersClient.checkClusterCreatorEqual(clusterId, myUserId)) {
            throw new ClientException("你不是这个群组的群主, 无法随意踹人!!!");
        }

        //2. 群组表操作
        userClusterFunc.quitCluster(userId, clusterId);

        //3. 维护 UserFunc 的加入群组数量
        usersFunc.opUserJoinClusterCount(userId, SystemSpecialCT.SUB, 1);
    }

    /**
     * 将所有人退出对应群组 (群主摧毁了群)
     */
    public void everyQuitCluster(Long clusterId) {

        // 维护群组表的所有人退出
        List<Long> findUsers = userClusterFunc.everyQuitCluster(clusterId);

        // 维护 UserFunc 的创建群组数量
        usersFunc.opUserCreateClusterCount(UserContext.getUI(), SystemSpecialCT.SUB, 1);

        // 对List中每个用户执行维护加入数量操作
        for (Long userId : findUsers) {
            usersFunc.opUserJoinClusterCount(userId, SystemSpecialCT.SUB, 1);
        }
    }

    /**
     * 获取群组中用户的基础清单, 之后可以根据需要发起对应用户详细信息的请求查询
     */
    public List<UserVO> getClusterUserList(@NotNull(message = "群组id不能为空") Long clusterId) {

        //1. 查中间表中所有用户id清单
        List<Long> toFind = usersFunc.getClusterUserIdList(clusterId);

        //2. 用 id 查用户基础表中id清单的用户信息
        List<UserVO> userVOList = usersFunc.getUserInfoByIds(toFind);

        return userVOList;
    }


    /**
     * 通过用户ids获取用户简单VO信息 (账号 / 是否管理员) 批量查询
     */
    public List<UserVO> getUserDOInfoBatch(List<Long> creatorUserIds) {
        return usersFunc.getUserDOInfoBatch(creatorUserIds);
    }

    /**
     * 计算中间表获取对应群组中用户数量
     */
    public Integer getClusterUserCount(Long clusterId) {
        return userClusterFunc.getClusterUserCount(clusterId);
    }
}
