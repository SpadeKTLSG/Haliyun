package xyz.spc.serve.auxiliary.config.feign;

import cn.hutool.json.JSONUtil;
import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.spc.common.funcpack.commu.errorcode.ServerError;
import xyz.spc.common.funcpack.commu.exception.ServiceException;
import xyz.spc.serve.auxiliary.common.context.UserContext;

/**
 * Feign配置
 */
@Configuration
@Slf4j
public class FeignConfig {


    @Bean
    public Logger.Level logLevel() {
        log.debug("配置Feign日志级别为FULL");
        return Logger.Level.FULL;
    }

    /**
     * 传递all用户信息到下游微服务
     */
    @Bean
    public RequestInterceptor allHolderRequestInterceptor() {
        log.debug("配置传递all用户信息到下游微服务");
        return template -> {

            if (UserContext.getUser() == null || UserContext.getUser().getId() == null) {
                throw new ServiceException(ServerError.SERVICE_RESOURCE_ERROR);
            }
            // 放入请求头中传递给下游微服务
            String userAllInfo = JSONUtil.toJsonStr(UserContext.getUser());
            template.header("user-all-info", userAllInfo);
        };
    }
}
