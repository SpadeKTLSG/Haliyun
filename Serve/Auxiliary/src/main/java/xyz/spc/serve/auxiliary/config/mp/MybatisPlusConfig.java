package xyz.spc.serve.auxiliary.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.yulichang.autoconfigure.consumer.MybatisPlusJoinPropertiesConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import xyz.spc.serve.auxiliary.config.mp.compo.CustomIdGenerator;
import xyz.spc.serve.auxiliary.config.mp.compo.MyMetaObjectHandler;

import javax.sql.DataSource;

/**
 * MybatisPlus配置
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {

    /**
     * 关闭逻辑删除 and 输出横幅
     */
    @Bean
    public MybatisPlusJoinPropertiesConsumer mybatisPlusJoinPropertiesConsumer() {
        log.debug("关闭逻辑删除 and 输出横幅");
        return prop -> prop
                .setBanner(false)
                .setSubTableLogic(false);
    }

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.debug("分页插件创建");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 元数据填充
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        log.debug("元数据填充创建");
        return new MyMetaObjectHandler();
    }

    /**
     * 自定义雪花算法 ID 生成器
     */
    @Bean
    @Primary
    public IdentifierGenerator idGenerator() {
        log.debug("自定义雪花算法 ID 生成器创建");
        return new CustomIdGenerator();
    }

    /**
     * MP需要替换SqlSessionFactory
     */
    @Primary
    @Bean("db1SqlSessionFactory")
    public SqlSessionFactory db1SqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean b1 = new MybatisSqlSessionFactoryBean();
        log.debug("SqlSessionFactory创建 | \"dataSourceLyz\" + dataSource.toString()");
        b1.setDataSource(dataSource);
        b1.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/Infra/Mapper/src/main/resources/mapper/*.xml"));

        return b1.getObject();
    }
}
