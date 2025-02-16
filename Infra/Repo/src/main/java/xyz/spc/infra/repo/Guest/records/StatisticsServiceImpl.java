package xyz.spc.infra.repo.Guest.records;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.records.StatisticsDO;
import xyz.spc.infra.mapper.Guest.records.StatisticsMapper;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, StatisticsDO> implements StatisticsService {
}
