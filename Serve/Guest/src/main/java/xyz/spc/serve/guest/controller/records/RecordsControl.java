package xyz.spc.serve.guest.controller.records;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Guest.records.StatisticsVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.guest.flow.RecordsFlow;

@Slf4j
@MLog
@Tag(name = "Records", description = "用户记录合集")
@RequestMapping("/Guest/records")
@RestController
@RequiredArgsConstructor
public class RecordsControl {

    // Flow
    private final RecordsFlow recordsFlow;


    //! Client


    //! Func


    //! ADD


    //! DELETE


    //! UPDATE


    /**
     * 增加对应累积信息字段通用接口
     */
    @PutMapping("/statistic/add_some_field")
    public Result<Object> addSomeField(
            @RequestParam String fieldName,
            @RequestParam Long targetUserId
    ) {
        recordsFlow.addSomeField(fieldName, targetUserId);
        return Result.success();
    }


    //! Query

    /**
     * 获取用户统计信息
     * (单个人, 不分页)
     */
    @GetMapping("/statistic/user_show")
    public Result<StatisticsVO> getUserStatistics(
            @RequestParam Long targetUserId
    ) {
        return Result.success(recordsFlow.getUserStatistics(targetUserId));
    }
}
