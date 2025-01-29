package xyz.spc.serve.auxiliary.config.mp.compo;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;


/**
 * 自定义雪花算法生成器
 */
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return SnowflakeIdUtil.nextId();
    }
}
