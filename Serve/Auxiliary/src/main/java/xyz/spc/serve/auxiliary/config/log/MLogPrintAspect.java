package xyz.spc.serve.auxiliary.config.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import xyz.spc.common.funcpack.exception.AbstractException;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Aspect
public class MLogPrintAspect {

    //todo 写入数据库, 需要Guide模块
    //* id
    //* success    tn    是否成功
    //* query_type    varchar(10)    请求类型
    //* route    varchar(256)    请求路径
    //* execute_time    varchar(256)    计时(自带ms)
    //* user_account    varchar(128)    用户账户

    /**
     * 打印类或方法上的 {@link MLog}, 并作入库
     */
    @Around("@within(xyz.spc.serve.auxiliary.config.log.MLog) || @annotation(xyz.spc.serve.auxiliary.config.log.MLog)")
    public Object printMLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = SystemClock.now();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String beginTime = DateUtil.now();
        Object result = null;
        boolean win = true;
        try {
            result = joinPoint.proceed(); //放行
        } catch (AbstractException e) {
            win = false;
            throw e; //只作记录, 仍然抛出异常
        } finally {
            //对象方法
            Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
            //获取方法上的注解 MLog
            MLog logAnnotation = Optional.ofNullable(targetMethod.getAnnotation(MLog.class)).orElse(joinPoint.getTarget().getClass().getAnnotation(MLog.class));

            //打印日志
            Objects.requireNonNull(logAnnotation);
            MLogPrint logPrint = new MLogPrint();
            logPrint.setBeginTime(beginTime);

            //开启了打印入参
            if (logAnnotation.input()) {
                logPrint.setInputParams(buildInput(joinPoint));
            }
            //开启了打印出参
            if (logAnnotation.output()) {
                logPrint.setOutputParams(result);
            }


            String methodType = "", requestURI = "";
            try {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (servletRequestAttributes != null) {
                    methodType = servletRequestAttributes.getRequest().getMethod();
                }
                if (servletRequestAttributes != null) {
                    requestURI = servletRequestAttributes.getRequest().getRequestURI();
                }
            } catch (Exception ignored) {
            }

            if (win) {
                log.debug("[Win]:[{}] {}, \t executeTime: {}ms, \t info: {}", methodType, requestURI, SystemClock.now() - startTime, JSON.toJSONString(logPrint));
            } else {
                log.warn("[Fail]:[{}] {}, \t executeTime: {}ms, \t info: {}", methodType, requestURI, SystemClock.now() - startTime, JSON.toJSONString(logPrint));
            }

        }
        return result;
    }

    /**
     * 构建入参
     */
    private Object[] buildInput(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object[] printArgs = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            if ((args[i] instanceof HttpServletRequest) || args[i] instanceof HttpServletResponse) {
                continue;
            }
            if (args[i] instanceof byte[]) {
                printArgs[i] = "byte array";
            } else if (args[i] instanceof MultipartFile) {
                printArgs[i] = "file";
            } else {
                printArgs[i] = args[i];
            }
        }

        return printArgs;
    }

    @Data
    private static class MLogPrint {

        private String beginTime;

        private Object[] inputParams;

        private Object outputParams;
    }
}

