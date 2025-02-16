package xyz.spc.infra.mapper.Money.standards;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Money.standards.CurrencyDO;

@Mapper
public interface CurrencyMapper extends MPJBaseMapper<CurrencyDO> {
}
