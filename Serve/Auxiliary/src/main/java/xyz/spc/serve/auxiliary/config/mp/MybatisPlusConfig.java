package xyz.spc.serve.auxiliary.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.yulichang.autoconfigure.consumer.MybatisPlusJoinPropertiesConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import xyz.spc.serve.auxiliary.config.mp.compo.CustomIdGenerator;
import xyz.spc.serve.auxiliary.config.mp.compo.MyMetaObjectHandler;

/**
 * MybatisPlus配置
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 关闭逻辑删除 and 输出横幅
     */
    @Bean
    public MybatisPlusJoinPropertiesConsumer mybatisPlusJoinPropertiesConsumer() {
        return prop -> prop
                .setBanner(false)
                .setSubTableLogic(false);
    }

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 元数据填充
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }

    /**
     * 自定义雪花算法 ID 生成器
     */
    @Bean
    @Primary
    public IdentifierGenerator idGenerator() {
        return new CustomIdGenerator();
    }
}
