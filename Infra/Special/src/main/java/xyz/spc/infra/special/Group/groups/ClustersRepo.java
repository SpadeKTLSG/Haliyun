package xyz.spc.infra.special.Cluster.groups;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Cluster.groups.ClusterDetailMapper;
import xyz.spc.infra.mapper.Cluster.groups.ClusterFuncMapper;
import xyz.spc.infra.mapper.Cluster.groups.ClusterMapper;
import xyz.spc.infra.repo.Cluster.groups.ClusterDetailService;
import xyz.spc.infra.repo.Cluster.groups.ClusterFuncService;
import xyz.spc.infra.repo.Cluster.groups.ClusterService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class ClustersRepo {

    public final ClusterService groupService;
    public final ClusterMapper groupMapper;
    public final ClusterDetailService groupDetailService;
    public final ClusterDetailMapper groupDetailMapper;
    public final ClusterFuncService groupFuncService;
    public final ClusterFuncMapper groupFuncMapper;


    //我的大DAO早已饥渴难耐
}
