package xyz.spc.infra.special.Cluster.managers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Cluster.managers.ClusterAuthMapper;
import xyz.spc.infra.repo.Cluster.managers.ClusterAuthService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class ClusterAuthsRepo {

    public final ClusterAuthService clusterAuthService;
    public final ClusterAuthMapper clusterAuthMapper;
}
