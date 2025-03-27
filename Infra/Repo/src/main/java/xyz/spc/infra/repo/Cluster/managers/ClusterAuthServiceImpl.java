package xyz.spc.infra.repo.Cluster.managers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.managers.ClusterAuthDO;
import xyz.spc.infra.mapper.Cluster.managers.ClusterAuthMapper;

@Service
@RequiredArgsConstructor
public class ClusterAuthServiceImpl extends ServiceImpl<ClusterAuthMapper, ClusterAuthDO> implements ClusterAuthService {
}
