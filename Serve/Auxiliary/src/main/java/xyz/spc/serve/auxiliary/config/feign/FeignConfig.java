package xyz.spc.serve.auxiliary.config.feign;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.optionals.OptionalDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.spc.common.funcpack.errorcode.ServerError;
import xyz.spc.common.funcpack.exception.ServiceException;
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
        return Logger.Level.BASIC;
    }


    @Bean
    public Encoder feignEncoder() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new JacksonEncoder(objectMapper);
    }

    @Bean
    public Decoder feignDecoder() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new OptionalDecoder(new JacksonDecoder(objectMapper));
    }

    /**
     * Feign配置传递用户信息到下游微服务
     */
    @Bean
    public RequestInterceptor allHolderRequestInterceptor() {
        log.debug("Feign配置传递用户信息到下游微服务");
        return template -> {

            if (UserContext.getUser() == null || UserContext.getUser().getId() == null) {
                log.debug("用户未登录, 不能使用Feign调用");
                throw new ServiceException(ServerError.SERVICE_RESOURCE_ERROR);
            }
            // 放入请求头中传递给下游微服务
            String userAllInfo = JSONUtil.toJsonStr(UserContext.getUser());
            template.header("user-all-info", userAllInfo);
        };
    }
}
