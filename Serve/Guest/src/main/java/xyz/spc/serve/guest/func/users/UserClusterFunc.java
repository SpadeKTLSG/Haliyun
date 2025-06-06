package xyz.spc.serve.guest.func.users;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Guest.users.UserClusterDO;
import xyz.spc.domain.model.Guest.users.UserCluster;
import xyz.spc.infra.special.Guest.users.UserClusterRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserClusterFunc {

    /**
     * Repo
     */
    private final UserClusterRepo userClusterRepo;

    /**
     * 获取用户的群组ids
     */
    public List<Long> getUsersClusterIds(Long id) {

        List<UserClusterDO> temp = userClusterRepo.userClusterMapper.selectList(
                Wrappers.lambdaQuery(UserClusterDO.class)
                        .eq(UserClusterDO::getUserId, id)
                        .orderByAsc(UserClusterDO::getSort)
        );

        return temp.stream().map(UserClusterDO::getClusterId).toList();
    }


    /**
     * 检查用户是否已经在群组中
     */
    public boolean checkUserJoinCluster(Long userId, Long clusterId) {

        return userClusterRepo.userClusterMapper.selectCount(
                Wrappers.lambdaQuery(UserClusterDO.class)
                        .eq(UserClusterDO::getUserId, userId)
                        .eq(UserClusterDO::getClusterId, clusterId)
        ) > 0;

    }


    /**
     * 创建 userId - clusterId 关系
     */
    public void joinCluster(Long userId, Long clusterId) {

        //? 收藏集 + 对应排序 功能在修改里面, 这个是新增关系

        //组装数据库字段
        Long id = SnowflakeIdUtil.nextId();

        UserClusterDO tmp = UserClusterDO
                .builder()
                .id(id)
                .userId(userId)
                .clusterId(clusterId)
                .collect(UserCluster.COLLECT_DEFAULT)
                .sort(0)
                .build();

        userClusterRepo.userClusterService.save(tmp);

    }

    /**
     * 删除 userId - clusterId 关系
     */
    public void quitCluster(Long userId, Long clusterId) {

        userClusterRepo.userClusterMapper.delete(
                Wrappers.lambdaQuery(UserClusterDO.class)
                        .eq(UserClusterDO::getUserId, userId)
                        .eq(UserClusterDO::getClusterId, clusterId)
        );
    }

    /**
     * 删除 * - clusterId 关系, 记录下受影响的 userId 到列表后续维护对应的记录数量
     */
    public List<Long> everyQuitCluster(Long clusterId) {


        // 查询 clusterId == input 的所有的用户id到list
        List<UserClusterDO> temp = userClusterRepo.userClusterMapper.selectList(
                Wrappers.lambdaQuery(UserClusterDO.class)
                        .eq(UserClusterDO::getClusterId, clusterId)
        );

        // 执行删除
        userClusterRepo.userClusterMapper.delete(
                Wrappers.lambdaQuery(UserClusterDO.class)
                        .eq(UserClusterDO::getClusterId, clusterId)
        );

        // 转化为id List
        List<Long> res = temp.stream()
                .map(UserClusterDO::getUserId)
                .toList();

        return res;
    }


    /**
     * 计算中间表获取对应群组中用户数量
     */
    public Integer getClusterUserCount(Long clusterId) {

        Integer count = Math.toIntExact(userClusterRepo.userClusterMapper.selectCount(
                Wrappers.lambdaQuery(UserClusterDO.class)
                        .eq(UserClusterDO::getClusterId, clusterId)
        ));

        return count;

    }
}
