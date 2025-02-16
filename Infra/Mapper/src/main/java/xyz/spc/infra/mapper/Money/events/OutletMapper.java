package xyz.spc.infra.mapper.Money.events;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Money.events.OutletDO;

@Mapper
public interface OutletMapper extends MPJBaseMapper<OutletDO> {
}
