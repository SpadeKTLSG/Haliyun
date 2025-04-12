package xyz.spc.serve.guest.func.records;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Guest.records.StatisticsDO;
import xyz.spc.infra.special.Guest.records.StatisticssRepo;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticFunc {

    /**
     * Repo
     */
    private final StatisticssRepo statisticssRepo;

    /**
     * 用户创建时候, 连带注册统计表, 做日常数据统计
     * ?warn: 此时 TL 里面是没有信息的, 还没注册
     */
    @Async
    public void registerStatistics(Long userId) {

        Long id = SnowflakeIdUtil.nextId();

        StatisticsDO statisticsDO = StatisticsDO.builder()
                .id(id)
                .userId(userId)
                .build(); // 剩下字段DB实现默认 0 值

        statisticssRepo.statisticsService.save(statisticsDO);
    }

    /**
     * 删除用户统计表 - 更新 逻辑删除
     */
    public void killUserAccountStatistics(Long userId) {

        // 1 查找
        StatisticsDO statisticsDO = Optional.of(statisticssRepo.statisticsService.getOne(
                Wrappers.lambdaQuery(StatisticsDO.class)
                        .eq(StatisticsDO::getUserId, userId)
        )).orElseThrow(
                () -> new ServiceException(ServerError.SERVICE_RESOURCE_ERROR)
        );


        statisticsDO.setS(rue);
        statisticssRepo.statisticsService.updateById(statisticsDO);
    }
}
