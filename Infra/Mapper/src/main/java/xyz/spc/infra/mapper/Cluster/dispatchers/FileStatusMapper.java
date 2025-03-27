package xyz.spc.infra.mapper.Cluster.dispatchers;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Cluster.dispatchers.FileStatusDO;

@Mapper
public interface FileStatusMapper extends MPJBaseMapper<FileStatusDO> {
}
