package io.github.shenbinglife.validators.anno.cfg;


import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.validators.StringNotBlankValidator;

import java.lang.annotation.*;

/**
 * 字符串不为空，不为null校验
 *
 * @author shenbing
 * @version 2017/11/17
 * @since 1.0
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ComplexValidateMeta(value = ValidatorNames.NOT_BLANK_VALIDATOR, clazz = StringNotBlankValidator.class)
public @interface NotBlank {

    /**
     * 默认使用的异常码
     */
    int ecode() default Consts.DEFAULT_ECODE;

    /**
     * 默认使用的异常信息
     */
    String emsg() default "${field}字符串不能为空";

    /**
     * 校验器组
     */
    String[] groups() default {};

    /**
     * 被校验字段名称
     */
    String field() default Consts.DEFAULT_FIELD;
}
