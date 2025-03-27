package xyz.spc.infra.mapper.Cluster.managers;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Cluster.managers.ClusterAuthDO;

@Mapper
public interface ClusterAuthMapper extends MPJBaseMapper<ClusterAuthDO> {
}
