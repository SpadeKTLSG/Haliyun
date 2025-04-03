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
}
