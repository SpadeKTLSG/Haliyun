package xyz.spc.infra.mapper.Guest.records;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.spc.domain.dos.Guest.records.StatisticsDO;

@Mapper
public interface StatisticsMapper extends MPJBaseMapper<StatisticsDO> {
}
