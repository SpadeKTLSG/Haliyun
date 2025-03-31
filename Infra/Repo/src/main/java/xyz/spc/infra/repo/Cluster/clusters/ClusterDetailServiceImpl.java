package xyz.spc.infra.repo.Cluster.clusters;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDetailDO;
import xyz.spc.infra.mapper.Cluster.clusters.ClusterDetailMapper;

@Service
@RequiredArgsConstructor
public class ClusterDetailServiceImpl extends ServiceImpl<ClusterDetailMapper, ClusterDetailDO> implements ClusterDetailService {
}
