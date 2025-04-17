package xyz.spc.serve.guest.flow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.domain.model.Guest.records.Statistics;
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
     * ? note : 使用场景是用户操作之后的设置, 异步执行
     */
    @Async
    public void addSomeField(String fieldName, Long targetUserId) {

        // 1 鉴权: 判断 这一 filedName 是否对应于 统计表的字段
        // 使用充血模型
        Statistics statistics = new Statistics();

        boolean isFieldName = statistics.isFieldName(fieldName);
        if (!isFieldName) {
            throw new ServiceException(ServerError.SERVICE_ILLEGAL_ERROR);
        }

        // 2 执行 +=1 操作, 下沉到 Repo层进行 分派
        statisticFunc.addSomeField(fieldName, targetUserId);
    }


    /**
     * 获取用户统计信息
     */
    public StatisticsVO getUserStatistics(Long targetUserId) {

        // 1 都是数量直接查
        return statisticFunc.getUserStatistics(targetUserId);
    }
}
