package xyz.spc.serve.auxiliary.config.mp;


import com.alibaba.druid.pool.DruidDataSource;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Druid配置
 */
@Slf4j
@Configuration
public class DruidConfig {


    /**
     * 设置Druid属性
     */
    @PostConstruct
    public void setProperties() {
        log.debug("设置Druid属性");
        System.setProperty("druid.mysql.usePingMethod", "false"); //解决druid 日志报错：discard long time none received connection:xxx
    }

    /**
     * 配置Druid数据源
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        log.debug("配置Druid数据源");
        return new DruidDataSource();
    }
}
