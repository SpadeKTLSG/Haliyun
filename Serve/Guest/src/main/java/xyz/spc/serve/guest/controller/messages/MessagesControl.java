package xyz.spc.serve.guest.controller.messages;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.dto.Guest.messages.SelfMailDTO;
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

    /**
     * 自己 发送消息 到 用户 B
     */
    @PostMapping("/send")
    public Result<Object> sendMes(
            @NonNull @RequestBody SelfMailDTO selfMailDTO
    ) {
        messagesFlow.sendMes(selfMailDTO);
        return Result.success();
    }


    //! DELETE


    /**
     * 删除某条消息 (双方对等)
     */
    @DeleteMapping("/delete/{mesId}")
    public Result<Object> deleteMes(
            @PathVariable Long mesId
    ){
        messagesFlow.deleteMes(mesId);
        return Result.success();
    }


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


    /**
     * 判断用户有无未读消息 (首页刷新的场景) - 直接返回未读消息数量, 首页展示
     */

}
