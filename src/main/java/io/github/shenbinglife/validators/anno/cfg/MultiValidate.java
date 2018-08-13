package io.github.shenbinglife.validators.anno.cfg;


import java.lang.annotation.*;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.validators.MultiValidator;

/**
 * 多重校验器, 可以配置多个校验器
 *
 * @author shenbing
 * @version 2017/11/16
 * @since 1.0
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ComplexValidateMeta(value = ValidatorNames.MULTI_VALIDATE_VALIDATOR, clazz = MultiValidator.class)
public @interface MultiValidate {

    /**
     * 校验器名称数组，优先使用校验器名称中的校验器，并忽略校验器类中的校验器
     */
    String[] value() default {};

    /**
     * 校验器类数组， 当校验器名称数组为空时，使用校验器类数组中的校验器
     */
    Class<? extends ComplexValidator>[] classes() default {};

    /**
     * 默认使用的异常码
     */
    int ecode() default Consts.DEFAULT_ECODE;

    /**
     * 默认使用的异常信息
     */
    String emsg() default "${field}参数校验失败";

    /**
     * 校验器组
     */
    String[] groups() default {};

    /**
     * 被校验字段名称
     */
    String field() default Consts.DEFAULT_FIELD;
}
