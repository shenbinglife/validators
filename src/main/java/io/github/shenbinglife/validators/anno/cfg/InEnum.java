package io.github.shenbinglife.validators.anno.cfg;


import java.lang.annotation.*;

import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.anno.meta.MsgFiller;
import io.github.shenbinglife.validators.validators.InEnumValidator;

/**
 * 校验对象在枚举中，默认将对象与枚举名称匹配。
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
@ComplexValidateMeta(value = ValidatorNames.IN_ENUM_VALIDATOR, clazz = InEnumValidator.class)
public @interface InEnum {

    /**
     * 枚举类型
     */
    @MsgFiller("enumClass")
    Class<? extends Enum> enumClass();

    /**
     * 枚举中的方法，将枚举方法的结果与被校验参数进行equals判断校验结果
     */
    String method() default "name";

    /**
     * 默认使用的异常码
     */
    int ecode() default Consts.DEFAULT_ECODE;

    /**
     * 默认使用的异常信息
     */
    String emsg() default "${field}必须属于指定枚举类型:${enumClass}";

    /**
     * 校验器组
     */
    String[] groups() default {};

    /**
     * 被校验字段名称
     */
    String field() default Consts.DEFAULT_FIELD;
}
