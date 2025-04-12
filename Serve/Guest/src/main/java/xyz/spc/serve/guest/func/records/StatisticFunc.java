package xyz.spc.serve.guest.func.records;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticFunc {


    /**
     * 用户创建时候, 连带注册统计表, 做日常数据统计
     * ?warn: 此时 TL 里面是没有信息的, 还没注册
     */
    @Async
    public void registerStatistics(Long userId) {

    }
}
