package xyz.spc.serve.auxiliary.common.interceptor.repeat;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.spc.common.constant.SysCacheKey;
import xyz.spc.common.funcpack.repeat.RepeatSubmit;
import xyz.spc.common.funcpack.repeat.RepeatSubmitInterceptor;
import xyz.spc.common.util.collecUtil.StringUtil;
import xyz.spc.common.util.webUtil.HttpsUtil;
import xyz.spc.serve.auxiliary.common.context.UserContext;
import xyz.spc.serve.auxiliary.config.redis.tool.RedisCacheGeneral;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 重复操作拦截器
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {

    private final RedisCacheGeneral redisCacheGeneral;

    // 重复参数 字段
    private final String REPEAT_PARAMS = "repeatParams";
    // 重复时间 字段
    private final String REPEAT_TIME = "repeatTime";


    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper repeatedlyRequest) {
            nowParams = HttpsUtil.getBodyString(repeatedlyRequest);
        }

        // body参数为空，获取Parameter的数据
        if (StringUtil.isEmpty(nowParams)) {
            nowParams = JSON.toJSONString(request.getParameterMap());
        }

        // nowData 为当前请求参数
        Map<String, Object> nowDataMap = new HashMap<String, Object>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());
        String url = request.getRequestURI();
        // 唯一标识（指定key + 请求地址 +  账号）
        String cacheRepeatKey = SysCacheKey.REPEAT_SUBMIT_KEY + url + UserContext.getUser().getAccount();

        // sessionObj 为上一次请求的参数
        Object sessionObj = redisCacheGeneral.getCacheObject(cacheRepeatKey);
        if (sessionObj != null) {
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if (sessionMap.containsKey(url)) {
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap, annotation.interval())) {
                    return true;
                }
            }
        }
        Map<String, Object> cacheMap = new HashMap<String, Object>();
        cacheMap.put(url, nowDataMap);
        redisCacheGeneral.setCacheObject(cacheRepeatKey, cacheMap, annotation.interval(), TimeUnit.MILLISECONDS);
        return false;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        return (time1 - time2) < interval;
    }
}
