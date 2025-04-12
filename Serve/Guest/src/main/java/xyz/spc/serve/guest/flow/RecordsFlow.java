package xyz.spc.serve.guest.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.gate.vo.Guest.records.StatisticsVO;
import xyz.spc.infra.feign.Guest.UsersClient;
import xyz.spc.serve.guest.func.records.StatisticFunc;
import xyz.spc.serve.guest.func.records.TombFunc;
import xyz.spc.serve.guest.func.users.UsersFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordsFlow {

    //Feign
    private final UsersClient usersClient;

    //Func
    private final UsersFunc usersFunc;
    private final TombFunc tombFunc;
    private final StatisticFunc statisticFunc;


    /**
     * 增加对应用户的累积信息字段通用接口 - 使用字段枚举通用化增加对应字段的方法 (+=1)
     */
    public void addSomeField(String fieldName, Long targetUserId) {
    }


    /**
     * 获取用户统计信息
     */
    public StatisticsVO getUserStatistics(Long targetUserId) {
        return null;
    }
}
