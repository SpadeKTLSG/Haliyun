package xyz.spc.infra.repo.Cluster.groups;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Cluster.groups.ClusterFuncDO;
import xyz.spc.infra.mapper.Cluster.groups.ClusterFuncMapper;

@Service
@RequiredArgsConstructor
public class ClusterFuncServiceImpl extends ServiceImpl<ClusterFuncMapper, ClusterFuncDO> implements ClusterFuncService {
}
