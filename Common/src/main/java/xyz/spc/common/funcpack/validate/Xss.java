package xyz.spc.common.funcpack.validate;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义xss校验注解
 * <p>
 * 需要在需要校验的方法上加上@Xss注解:     @Xss(message = "用户账号不能包含脚本字符")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {Xss.XssValidator.class})
public @interface Xss {
    String message() default "不允许任何脚本运行";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 自定义xss校验注解实现
     */
    @Component
    class XssValidator implements ConstraintValidator<Xss, String> {

        private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

        public static boolean containsHtml(String value) {
            Pattern pattern = Pattern.compile(HTML_PATTERN);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (StringUtils.isBlank(value)) {
                return true;
            }
            return !containsHtml(value);
        }
    }
}
