package xyz.spc.infra.mapper.Cluster.managers;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Cluster.managers.FileLockDO;

@Mapper
public interface FileLockMapper extends MPJBaseMapper<FileLockDO> {
}
