package xyz.spc.common.funcpack.repeat;

import xyz.spc.common.constant.UploadDownloadCT;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义注解防止表单重复提交
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    int interval() default UploadDownloadCT.SAME_URL_DATA_TIME;

    /**
     * 锁定时间单位，默认毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;


    /**
     * 提示消息
     */
    String message() default "太快了哦, 请稍候再试!";
}
