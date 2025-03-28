package xyz.spc.serve.cluster.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.cluster.func.clusters.ClustersFunc;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClustersFlow {


    //Feign
    private final ClustersClient clustersClient;

    //Func
    private final ClustersFunc clustersFunc;

    public List<String> getClusterNamesByIds(List<Long> groupIds) {
        return clustersFunc.getClusterNamesByIds(groupIds);
    }
}
