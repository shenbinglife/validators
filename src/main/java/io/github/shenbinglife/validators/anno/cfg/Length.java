package io.github.shenbinglife.validators.anno.cfg;

import java.lang.annotation.*;

import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.anno.meta.MsgFiller;
import io.github.shenbinglife.validators.validators.LengthValidator;

/**
 * 字符串、数组、集合的长度校验
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
@ComplexValidateMeta(value = ValidatorNames.LENGTH_VALIDATOR, clazz = LengthValidator.class)
public @interface Length {

    /**
     * 最小长度, 小于等于
     */
    @MsgFiller("min")
    int min() default Integer.MIN_VALUE;

    /**
     * 最大长度，大于等于
     */
    @MsgFiller("max")
    int max() default Integer.MAX_VALUE;

    /**
     * 如果长度为0，判断能否通过校验。
     * <P>
     * 默认为false
     * </P>
     * <P>
     * true：表示长度为零，默认通过校验；false：表示长度为0，仍然要进行长度验证
     * </P>
     */
    boolean zeroAble() default false;

    /**
     * 默认使用的异常码
     */
    int ecode() default Consts.DEFAULT_ECODE;

    /**
     * 默认使用的异常信息
     */
    String emsg() default "${field}长度必须在指定范围内, 最小: ${min}, 最大: ${max}";

    /**
     * 校验器组
     */
    String[] groups() default {};

    /**
     * 被校验字段名称
     */
    String field() default Consts.DEFAULT_FIELD;
}
