package xyz.spc.serve.auxiliary.config.senti;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import xyz.spc.common.funcpack.Result;

/**
 * 自定义流控策略
 */
@Slf4j
public class CustomBlockHandler {

    /**
     * 验证码获取方法限流处理
     */
    public static Result<String> getLoginCodeBlockHandlerMethod(String phone, BlockException ex) { //注意参数列表需要与原方法一致
        log.debug("getLoginCode方法被限流");
        return Result.fail("当前访问网站人数过多，请稍后再试...");
    }
}
