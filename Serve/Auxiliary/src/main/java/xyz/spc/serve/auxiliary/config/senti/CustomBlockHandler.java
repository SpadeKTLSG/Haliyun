package xyz.spc.serve.auxiliary.config.senti;


import lombok.extern.slf4j.Slf4j;
import xyz.spc.common.funcpack.commu.Result;

/**
 * 自定义流控策略
 */
@Slf4j
public class CustomBlockHandler {

    public static Result<String> getLoginCodeBlockHandlerMethod() {
        log.debug("getLoginCode方法被限流");
        return Result.fail("当前访问网站人数过多，请稍后再试...");
    }
}
