package xyz.spc.serve.guest.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.spc.common.funcpack.repeat.RepeatSubmitInterceptor;
import xyz.spc.serve.guest.common.interceptor.login.GreatLoginInterceptor;
import xyz.spc.serve.guest.common.interceptor.login.GreatTokenRefreshInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * WebMvc配置
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class GreatWebMvcConfig implements WebMvcConfigurer {


    private final GreatLoginInterceptor greatLoginInterceptor;
    private final GreatTokenRefreshInterceptor greatTokenRefreshInterceptor;
    private final RepeatSubmitInterceptor repeatSubmitInterceptor;

    /**
     * 自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        log.debug("自定义拦截器启动!");

        String[] exPath = {
                //登录/code
                "/Guest/users/login",
                "/Guest/users/register",
                "/Guest/users/code",
                "/favicon.ico", "/Guest.html", //浏览器页面
                "/initialize/dispatcher-servlet", //初始化请求
                "/swagger-ui/**", "/swagger-ui.html", "/doc.html", "/webjars/**", "/swagger-resources/**", "/swagger-ui/**", "/v3/**", "/error"};

        //1 登录拦截器
        registry.addInterceptor(greatLoginInterceptor)
                .excludePathPatterns(exPath) //排除登录接口
                .order(0);

        //2 token刷新拦截器
        registry.addInterceptor(greatTokenRefreshInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(exPath) //排除登录接口
                .order(1);

        //3 重复提交拦截器
        registry.addInterceptor(repeatSubmitInterceptor)
                .addPathPatterns("/**") //对所有请求都进行拦截
                .order(2);
    }


    /**
     * 配置消息转换器
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 默认第0个转换器是StringHttpMessageConverter，处理String数据的转换
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(1, new ByteArrayHttpMessageConverter());// 默认第1个转换器是ByteArrayHttpMessageConverter，处理byte[]数据的转换(springdoc问题)
        converters.add(2, fastJsonHttpMessageConverter);// 添加FastJson的转换器, 放在第2个位置
    }


    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/").resourceChain(false);
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 支持 url 中传入 <>[\]^`{|} 这些特殊字符.
     */
    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory webServerFactory = new TomcatServletWebServerFactory();

        // 添加对 URL 中特殊符号的支持.
        webServerFactory.addConnectorCustomizers(connector -> {
            connector.setProperty("relaxedPathChars", "<>[\\]^`{|}%[]");
            connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}%[]");
        });
        return webServerFactory;
    }

    /**
     * 跨域配置(网关本身有做跨域, 这里只是调试方便)
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //允许发送Cookie信息
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径, 应用到所有请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
