package xyz.spc.serve.auxiliary.config.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 自定义Spring事件监听器 (通用模板)
 */
@Component
@Slf4j
public class SkEventListener<T> {

    @EventListener(SkEvent.class)
    public void handleMyEvent(SkEvent<T> event) {
        log.debug("监听到事件:{}", event);

        // 处理事件: 通过消息进行处理
        String mes = event.getMessage();
        switch (mes) {
            case "deleteFileInHDFSBy2Id" ->{
                handleFileUpload(event.getData());
            }
            default ->{
                log.warn("未知事件类型: {}", event.getMessage());
            }
        }

    }

}
