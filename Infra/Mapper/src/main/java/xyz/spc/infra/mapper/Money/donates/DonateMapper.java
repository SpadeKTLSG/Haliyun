package xyz.spc.infra.mapper.Money.donates;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Money.donates.DonateDO;

@Mapper
public interface DonateMapper extends MPJBaseMapper<DonateDO> {
}
