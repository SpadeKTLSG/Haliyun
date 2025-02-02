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
        return Logger.Level.BASIC;
    }

    /**
     * 传递all用户信息(管理/顾客)到下游微服务
     */
    @Bean
    public RequestInterceptor allHolderRequestInterceptor() {
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
