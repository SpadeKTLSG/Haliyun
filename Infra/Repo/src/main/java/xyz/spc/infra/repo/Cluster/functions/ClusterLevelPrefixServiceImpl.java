package xyz.spc.infra.repo.Cluster.functions;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.functions.ClusterLevelPrefixDO;
import xyz.spc.infra.mapper.Cluster.functions.ClusterLevelPrefixMapper;

@Service
@RequiredArgsConstructor
public class ClusterLevelPrefixServiceImpl extends ServiceImpl<ClusterLevelPrefixMapper, ClusterLevelPrefixDO> implements ClusterLevelPrefixService {
}
