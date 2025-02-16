package xyz.spc.infra.mapper.Guide.logs;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Guide.logs.FileOpLogDO;

@Mapper
public interface FileOpLogMapper extends MPJBaseMapper<FileOpLogDO> {
}
