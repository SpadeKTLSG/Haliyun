package xyz.spc.infra.special.Guest.records;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.records.StatisticsDO;
import xyz.spc.domain.model.Guest.records.Statistics;
import xyz.spc.infra.mapper.Guest.records.StatisticsMapper;
import xyz.spc.infra.repo.Guest.records.StatisticsService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class StatisticssRepo {

    public final StatisticsService statisticsService;
    public final StatisticsMapper statisticsMapper;

    /**
     * 获取统计表的字段对应初始化的 Wrapper
     */
    public LambdaUpdateWrapper<StatisticsDO> selectFieldWrapper(String fieldName) {
        Statistics tmp = new Statistics();

        // 选择对应统计表的字段进行 自增更新 (Lambda没法做自增, 因此使用 SQL 语句设置)
        for (String field : tmp.getFieldName()) {
            if (field.equals(fieldName)) {

                return Wrappers.lambdaUpdate(StatisticsDO.class)
                        .setSql(fieldName + " = " + fieldName + " + 1");

            }
        }

        return null;
    }
}
