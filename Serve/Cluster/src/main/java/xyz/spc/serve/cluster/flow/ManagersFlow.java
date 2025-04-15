package xyz.spc.serve.cluster.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.gate.vo.Cluster.managers.ClusterAuthVO;
import xyz.spc.serve.cluster.func.managers.ClusterAuthFunc;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagersFlow {

    //Feign


    //Func
    private final ClusterAuthFunc clusterAuthFunc;


    /**
     * 分页查询群组中所有用户的权限
     * (详情也是复用这个数据)
     */
    public List<ClusterAuthVO> getAllClusterAuths(Long targetClusterId, PageRequest pageRequest) {

        // 1 查基本对象信息
    }
}
