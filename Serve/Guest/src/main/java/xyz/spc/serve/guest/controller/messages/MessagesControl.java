package xyz.spc.serve.guest.controller.messages;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Guest.messages.SelfMailVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.guest.flow.MessagesFlow;

import java.util.List;

@Slf4j
@MLog
@Tag(name = "Users", description = "用户消息合集")
@RequestMapping("/Guest/messages")
@RestController
@RequiredArgsConstructor
public class MessagesControl {

    // Flow
    private final MessagesFlow messagesFlow;


    //! Client


    //! Func


    //! ADD


    //! DELETE


    //! UPDATE


    //! Query


    /**
     * 用户消息列表 - 收件箱 / 发件箱
     * (直接查询全部, 没有分页)
     * ? 使用入参来判断是收件箱 (0) - 默认 还是发件箱 (1)
     */
    @GetMapping("/list")
    public Result<List<SelfMailVO>> listMyMes(
            @RequestParam
            @NonNull
            Integer orderType
    ) {
        return Result.success(messagesFlow.listMyMes(orderType));
    }


    /**
     * 查看消息详情 - 收件箱 / 发件箱
     */
    @GetMapping("/detail")
    public Result<SelfMailVO> getMyMesDetail(
            @RequestParam
            @NonNull
            Long mesId,

            @RequestParam
            @NonNull
            Integer orderType
    ) {
        return Result.success(messagesFlow.getMyMesDetail(mesId, orderType));
    }

}
