package xyz.spc.infra.special.Guest.records;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guest.records.StatisticsMapper;
import xyz.spc.infra.repo.Guest.records.StatisticsService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class StatisticssRepo {

    public final StatisticsService statisticsService;
    public final StatisticsMapper statisticsMapper;
}
