package xyz.spc.infra.mapper.Cluster.clusters;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Cluster.clusters.ClusterDO;

@Mapper
public interface ClusterMapper extends MPJBaseMapper<ClusterDO> {
}
