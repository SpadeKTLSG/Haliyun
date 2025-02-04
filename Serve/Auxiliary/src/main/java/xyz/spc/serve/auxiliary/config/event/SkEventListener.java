package xyz.spc.serve.auxiliary.config.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 自定义Spring事件监听器
 */
@Component
@Slf4j
public class SkEventListener {

    @EventListener(SkEvent.class)
    public void handleMyEvent(SkEvent event) {
        log.debug("监听到事件:{}", event);
        //if ...
    }

}
