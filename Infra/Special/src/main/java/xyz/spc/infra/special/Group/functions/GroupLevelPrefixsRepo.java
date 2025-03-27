package xyz.spc.infra.special.Cluster.functions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Cluster.functions.ClusterLevelPrefixMapper;
import xyz.spc.infra.repo.Cluster.functions.ClusterLevelPrefixService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class ClusterLevelPrefixsRepo {

    public final ClusterLevelPrefixService groupLevelPrefixsService;
    public final ClusterLevelPrefixMapper groupLevelPrefixsMapper;
}
