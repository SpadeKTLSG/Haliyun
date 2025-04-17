package xyz.spc.serve.data.special;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.auxiliary.config.event.SkEvent;
import xyz.spc.serve.data.func.files.FilesFunc;

import java.util.Map;

/**
 * 自定义Spring事件监听器  (数据模块)
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SkEventListenerData<T> {


    //Feign
    private final ClustersClient clustersClient;

    //Func
    private final FilesFunc filesFunc;


    @EventListener(SkEvent.class)
    public void handleMyEvent(SkEvent<T> event) {
        log.debug("监听到事件:{}", event);

        // 处理事件: 通过消息进行处理
        String mes = event.getMessage();
        switch (mes) {

            case "deleteFileInHDFSBy2Id" -> {
                Map<String, Long> data = (Map<String, Long>) event.getData();
                Long clusterId = data.get("clusterId");
                Long fileId = data.get("fileId");

                filesFunc.deleteFileInHDFSBy2Id(fileId, clusterId);
            }
            default -> {
                log.warn("未知事件类型: {}", event.getMessage());
            }
        }

    }

}
