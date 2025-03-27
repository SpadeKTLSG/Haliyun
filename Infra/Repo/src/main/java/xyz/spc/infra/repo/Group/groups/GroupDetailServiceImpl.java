package xyz.spc.infra.repo.Cluster.groups;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.groups.ClusterDetailDO;
import xyz.spc.infra.mapper.Cluster.groups.ClusterDetailMapper;

@Service
@RequiredArgsConstructor
public class ClusterDetailServiceImpl extends ServiceImpl<ClusterDetailMapper, ClusterDetailDO> implements ClusterDetailService {
}
