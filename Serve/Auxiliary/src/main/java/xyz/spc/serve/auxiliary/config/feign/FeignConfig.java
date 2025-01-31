package xyz.spc.serve.auxiliary.config.feign;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            // 获取登录用户(员工/顾客)信息
            String userAllInfo = null;
            //todo
//            if  (UserHolder.getUser() == null || UserHolder.getUser().getId() == null) {
//                log.warn("哦哦, RPC请求头中没有任何家伙的Context信息...");
//                return;
//            }
//            // 如果不为空则放入请求头中，传递给下游微服务
//            // note: 同时仅仅存在一种用户信息: 顾客, 打成JSON字符串放入请求头中后面解析
//
//            if (UserHolder.getUser() != null && UserHolder.getUser().getId() != null) {
//                userAllInfo = JSONUtil.toJsonStr(UserHolder.getUser());
//                template.header("user_type", "guest");
//            }

            template.header("user-all-info", userAllInfo);
        };
    }
}
