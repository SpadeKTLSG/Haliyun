package xyz.spc.serve.guest.controller.messages;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.guest.flow.MessagesFlow;

@Slf4j
@MLog
@Tag(name = "Users", description = "用户消息合集")
@RequestMapping("/Guest/messages")
@RestController
@RequiredArgsConstructor
public class MessagesControl {

    // Flow
    private final MessagesFlow messagesFlow;

}
