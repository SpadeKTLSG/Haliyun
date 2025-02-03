package xyz.spc.serve.auxiliary.config.boot;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import xyz.spc.serve.auxiliary.config.boot.init.ApplicationContentPostProcessor;
import xyz.spc.serve.auxiliary.config.boot.safa.FastJsonSafeMode;

/**
 * 应用基础自动装配
 */
@Slf4j
public class ApplicationBaseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextHolder congoApplicationContextHolder() {
        log.debug("配置应用上下文持有器");
        return new ApplicationContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContentPostProcessor congoApplicationContentPostProcessor(ApplicationContext applicationContext) {
        log.debug("配置应用初始化后置处理器");
        return new ApplicationContentPostProcessor(applicationContext);
    }

    /**
     * FastJson安全模式，开启后关闭类型隐式传递
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "framework.fastjson.safa-mode", havingValue = "true")
    public FastJsonSafeMode congoFastJsonSafeMode() {
        log.debug("配置FastJson安全模式");
        return new FastJsonSafeMode();
    }
}
