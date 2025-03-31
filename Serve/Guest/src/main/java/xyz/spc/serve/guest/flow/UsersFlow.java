package xyz.spc.serve.guest.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.spc.gate.dto.Guest.users.UserDTO;
import xyz.spc.gate.vo.Guest.levels.LevelVO;
import xyz.spc.gate.vo.Guest.users.UserGreatVO;
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

        //注册用户核心表
        if (!usersFunc.registerCore(userDTO)) {
            return false;
        }
        //注册统计表
        statisticFunc.registerStatistics(userDTO);
        //注册坟墓表
        tombFunc.registerTomb(userDTO);

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

        //文件: 删除所有文件, 清理历史

        //日志: 定时清理失效用户的日志, 降低管理日志压力

        //用户: 注销用户基础联表三张信息
        usersFunc.killUserAccount(id);
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
}
