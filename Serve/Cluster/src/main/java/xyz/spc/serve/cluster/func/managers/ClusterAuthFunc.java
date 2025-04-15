package xyz.spc.serve.cluster.func.managers;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.domain.dos.Cluster.managers.ClusterAuthDO;
import xyz.spc.infra.special.Cluster.managers.ClusterAuthsRepo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterAuthFunc {

    // Repo
    private final ClusterAuthsRepo clusterAuthsRepo;


    /**
     * 查询群组中所有用户的权限行
     * ? note使用 MP 的分页查询
     */
    public List<ClusterAuthDO> getAllClusterMemberAuths(Long targetClusterId, PageRequest pageRequest) {

        Integer current = pageRequest.getCurrent();
        Integer size = pageRequest.getSize();

        List<ClusterAuthDO> res = clusterAuthsRepo.clusterAuthService.list(Wrappers.lambdaQuery(ClusterAuthDO.class)
                .eq(ClusterAuthDO::getClusterId, targetClusterId)
                .last("LIMIT " + (current - 1) * size + "," + size)
        );

        return res;
    }
}
