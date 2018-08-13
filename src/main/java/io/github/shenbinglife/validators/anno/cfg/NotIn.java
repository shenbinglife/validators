package io.github.shenbinglife.validators.anno.cfg;

import java.lang.annotation.*;

import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.anno.meta.MsgFiller;
import io.github.shenbinglife.validators.validators.NotInValidator;


/**
 * 校验字符串不存在数组中
 *
 * <P>
 * 默认被校验对象是null时，通过校验
 * </P>
 * 
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ComplexValidateMeta(value = ValidatorNames.Not_IN_VALIDATOR, clazz = NotInValidator.class)
public @interface NotIn {

    @MsgFiller("value")
    String[] value() default {};

    /**
     * 默认使用的异常码
     */
    int ecode() default Consts.DEFAULT_ECODE;

    /**
     * 默认使用的异常信息
     */
    String emsg() default "${field}必须排除在指定的数组中: ${value}";

    /**
     * 校验器组
     */
    String[] groups() default {};

    /**
     * 被校验字段名称
     */
    String field() default Consts.DEFAULT_FIELD;
}
