package xyz.spc.infra.mapper.Group.managers;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Group.managers.FileLockDO;

@Mapper
public interface FileLockMapper extends MPJBaseMapper<FileLockDO> {
}
