package xyz.spc.common.funcpack.validate;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;
import xyz.spc.common.util.collecUtil.StringUtil;

import java.lang.annotation.*;

/**
 * 不能包含中文字符校验
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER}) //定义可以在字段上使用
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotChinese.isContainChinese.class)
public @interface NotChinese {

    String message() default "不能包含中文字符";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 不能包含中文字符校验实现
     */
    @Component
    class isContainChinese implements ConstraintValidator<NotChinese, String> {

        @Override
        public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
            return !StringUtil.isContainsChinese(str);
        }
    }
}
