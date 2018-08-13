package io.github.shenbinglife.validators.anno.cfg;

import java.lang.annotation.*;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.validators.EachValidator;


/**
 * 对数组、集合、Map元素进行校验， 注意，Map对象，只校验内部的值列表
 *
 * <P>
 * 对象为null， 忽略校验
 * </P>
 * <P>
 * 如果不是数组或集合、Map，则忽略校验
 * </P>
 *
 * @author shenbing
 * @version 2018/2/4
 * @since since
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ComplexValidateMeta(value = ValidatorNames.EACH_VALIDATOR, clazz = EachValidator.class)
public @interface Each {

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
