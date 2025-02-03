package xyz.spc.serve.auxiliary.config.boot.safa;

import org.springframework.beans.factory.InitializingBean;

/**
 * FastJson安全模式，开启后关闭类型隐式传递
 */
public class FastJsonSafeMode implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        System.setProperty("fastjson2.parser.safeMode", "true");
    }
}
