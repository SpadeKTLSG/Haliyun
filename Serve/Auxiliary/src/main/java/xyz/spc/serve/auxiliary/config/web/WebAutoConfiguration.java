package xyz.spc.serve.auxiliary.config.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xyz.spc.serve.auxiliary.config.web.compo.GlobalExceptionHandler;
import xyz.spc.serve.auxiliary.config.web.compo.InitializeDispatcherServletController;
import xyz.spc.serve.auxiliary.config.web.compo.InitializeDispatcherServletHandler;


/**
 * Web 组件自动装配
 */
@Slf4j
@Component
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        log.debug("全局异常处理器装配完成");
        return new GlobalExceptionHandler();
    }

    @Bean
    public InitializeDispatcherServletController initializeDispatcherServletController() {
        log.debug("DispatcherServlet控制器初始化器装配完成");
        return new InitializeDispatcherServletController();
    }

    @Bean
    public RestTemplate simpleRestTemplate(ClientHttpRequestFactory factory) {
        log.debug("RestTemplate装配完成");
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        log.debug("ClientHttpRequestFactory装配完成");
        return factory;
    }

    @Bean
    public InitializeDispatcherServletHandler initializeDispatcherServletHandler(RestTemplate simpleRestTemplate, ConfigurableEnvironment configurableEnvironment) {
        log.debug("DispatcherServlet处理器初始化器装配完成");
        return new InitializeDispatcherServletHandler(simpleRestTemplate, configurableEnvironment);
    }
}
