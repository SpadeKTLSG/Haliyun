package xyz.spc.infra.repo.Cluster.clusters;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.clusters.ClusterFuncDO;
import xyz.spc.infra.mapper.Cluster.clusters.ClusterFuncMapper;

@Service
@RequiredArgsConstructor
public class ClusterFuncServiceImpl extends ServiceImpl<ClusterFuncMapper, ClusterFuncDO> implements ClusterFuncService {
}
