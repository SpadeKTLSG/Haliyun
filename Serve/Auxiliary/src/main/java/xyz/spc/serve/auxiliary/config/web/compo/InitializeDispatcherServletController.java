package xyz.spc.serve.auxiliary.config.web.compo;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.constant.SystemSpecialCT;


/**
 * 初始化 {@link org.springframework.web.servlet.DispatcherServlet}
 */
@Slf4j
@RestController
public final class InitializeDispatcherServletController {

    @GetMapping(SystemSpecialCT.INITIALIZE_BETTER_PATH)
    @Operation(hidden = true)
    public void initializeDispatcherServlet() {
        log.debug("初始化 dispatcherServlet 以改进接口的首次响应时间...");
    }
}
