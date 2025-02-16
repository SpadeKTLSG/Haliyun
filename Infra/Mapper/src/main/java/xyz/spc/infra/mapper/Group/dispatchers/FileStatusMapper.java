package xyz.spc.infra.mapper.Group.dispatchers;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Group.dispatchers.FileStatusDO;

@Mapper
public interface FileStatusMapper extends MPJBaseMapper<FileStatusDO> {
}
