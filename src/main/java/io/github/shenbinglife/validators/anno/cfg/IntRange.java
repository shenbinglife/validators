package io.github.shenbinglife.validators.anno.cfg;


import java.lang.annotation.*;

import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.anno.meta.MsgFiller;
import io.github.shenbinglife.validators.validators.IntRangeValidator;

/**
 * 判断数字在int范围内
 *
 * <P>
 * 默认被校验对象是null时，通过校验
 * </P>
 *
 * @author shenbing
 * @version 2017/11/17
 * @since 1.0
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ComplexValidateMeta(value = ValidatorNames.INT_RANGE_VALIDATOR, clazz = IntRangeValidator.class)
public @interface IntRange {

    /**
     * 小于等于
     */
    @MsgFiller("min")
    int min() default Integer.MIN_VALUE;

    /**
     * 大于等于
     */
    @MsgFiller("max")
    int max() default Integer.MAX_VALUE;

    /**
     * 默认使用的异常码
     */
    int ecode() default Consts.DEFAULT_ECODE;

    /**
     * 默认使用的异常信息
     */
    String emsg() default "${field}数字必须在指定范围内, 最小: ${min}, 最大: ${max}";

    /**
     * 校验器组
     */
    String[] groups() default {};

    /**
     * 被校验字段名称
     */
    String field() default Consts.DEFAULT_FIELD;
}
