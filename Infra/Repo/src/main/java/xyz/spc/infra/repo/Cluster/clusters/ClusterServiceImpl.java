package xyz.spc.infra.repo.Cluster.clusters;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;
import xyz.spc.infra.mapper.Cluster.clusters.ClusterMapper;

@Service
@RequiredArgsConstructor
public class ClusterServiceImpl extends ServiceImpl<ClusterMapper, ClusterDO> implements ClusterService {
}
