package xyz.spc.serve.auxiliary.config.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 自定义事件发布器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyEventPublisher<T> {

    private final ApplicationEventPublisher publisher;

    /**
     * 发布事件
     *
     * @param message 事件消息
     * @param data    事件数据
     */
    public void publishEvent(String message, T data) {
        SkEvent<T> event = new SkEvent<>(message, data);
        log.debug("发布事件:{}", event);
        publisher.publishEvent(event);
    }
}
