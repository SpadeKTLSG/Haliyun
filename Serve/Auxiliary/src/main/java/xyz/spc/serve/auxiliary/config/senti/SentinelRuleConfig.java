package xyz.spc.serve.auxiliary.config.senti;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import xyz.spc.common.constant.sentinel.FlowLimitCT;
import xyz.spc.common.constant.sentinel.SentinelPath;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化限流配置
 */
@Slf4j
@Component
public class SentinelRuleConfig implements InitializingBean {


    /**
     * 限流配置
     */
    @Override
    public void afterPropertiesSet() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule getCodeRule = new FlowRule();

        //! 验证码
        getCodeRule.setResource(SentinelPath.GET_LOGIN_CODE_PATH); // 限流路径
        getCodeRule.setGrade(RuleConstant.FLOW_GRADE_QPS); // 使用QPS模式
        getCodeRule.setCount(FlowLimitCT.MAX_QPS_COUNT); // 限流阈值 QPS

        rules.add(getCodeRule);

        FlowRuleManager.loadRules(rules);
        log.debug("SentinelRuleConfig init success");
    }
}
