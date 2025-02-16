package xyz.spc.serve.auxiliary.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.yulichang.injector.MPJSqlInjector;
import com.github.yulichang.interceptor.MPJInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import xyz.spc.serve.auxiliary.config.mp.compo.CustomIdGenerator;

import javax.sql.DataSource;

/**
 * MybatisPlus配置
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {


    /**
     * 插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.debug("分页插件创建");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        return interceptor;
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
     * 关联SqlSessionFactory与GlobalConfig
     * 设置mybatis 拦截器
     * MP需要替换SqlSessionFactory
     * issue: <a href="https://mybatis-plus-join.github.io/pages/problem.html">...</a>
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        log.debug("SqlSessionFactory创建 | \"dataSourceLyz\" + dataSource.toString()");
        sessionFactory.setDataSource(dataSource);
        // 关联SqlSessionFactory与GlobalConfig
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(new MPJSqlInjector());
        sessionFactory.setGlobalConfig(globalConfig);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/Infra/Mapper/src/main/resources/mapper/*.xml"));
        // MPJ feature 添加拦截器 MPJInterceptor需要放在最后面
        sessionFactory.setPlugins(new MPJInterceptor()); //MPJ拦截器sessionFactory.setPlugins(new MPJInterceptor());

        // 其他配置 略
        return sessionFactory.getObject();
    }
}
