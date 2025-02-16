package xyz.spc.infra.mapper.Guide.counts;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Guide.counts.PlatformStatusDO;

@Mapper
public interface PlatformStatusMapper extends MPJBaseMapper<PlatformStatusDO> {
}
