package io.github.shenbinglife.validators.anno.cfg;


import java.lang.annotation.*;

import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.anno.meta.MsgFiller;
import io.github.shenbinglife.validators.validators.RegexValidator;

/**
 * 正则表达式校验
 * <P>
 * 默认被校验对象是null时，通过校验
 * </P>
 *
 * @author shenbing
 * @version 2017/11/16
 * @since 1.0.0
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ComplexValidateMeta(value = ValidatorNames.REGEX_VALIDATOR, clazz = RegexValidator.class)
public @interface Regex {

    @MsgFiller("pattern")
    String pattern() default "";

    /**
     * 如果长度为0，判断能否通过校验。
     * <P>
     * 默认为false
     * </P>
     * <P>
     * true：表示长度为零，默认通过校验；false：表示长度为0，仍然要进行正则验证
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
    String emsg() default "${field}不匹配正则表达式: ${pattern}";

    /**
     * 校验器组
     */
    String[] groups() default {};

    /**
     * 被校验字段名称
     */
    String field() default Consts.DEFAULT_FIELD;
}
