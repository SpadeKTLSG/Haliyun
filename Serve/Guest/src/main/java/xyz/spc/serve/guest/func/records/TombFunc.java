package xyz.spc.serve.guest.func.records;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TombFunc {

    /**
     * 注册坟墓表, 做有趣事情的记录
     * ?warn: 此时 TL 里面是没有信息的, 还没注册
     */
    @Async
    public void registerTomb(Long userId) {
        //todo
    }


}
