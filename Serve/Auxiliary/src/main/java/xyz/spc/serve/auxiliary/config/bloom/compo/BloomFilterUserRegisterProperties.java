package xyz.spc.serve.auxiliary.config.bloom.compo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = BloomFilterUserRegisterProperties.PREFIX)
public class BloomFilterUserRegisterProperties {

    public static final String PREFIX = "xyz.spc.bloom-filter.users.register";

    /**
     * 布隆过滤器默认实例名称
     */
    private String name = "bloom_filter";

    /**
     * 每个元素的预期插入量
     */
    private Long expectedInsertions = 1024L;

    /**
     * 预期错误概率
     */
    private Double falseProbability = 0.03D;
}
