package io.github.shenbinglife.validators.anno.cfg;

import java.lang.annotation.*;

import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidatorNames;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.validators.ReflectValidator;

/**
 * 通过反射调用被校验参数，并判断反射方法的结果
 *
 * <P>
 * 默认被校验对象是null时，通过校验
 * </P>
 * 
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ComplexValidateMeta(value = ValidatorNames.REFLECT_VALIDATOR, clazz = ReflectValidator.class)
public @interface Reflect {

    /**
     * 反射要执行的类
     */
    Class<?> target();

    /**
     * 反射要执行的方法
     */
    String method();

    /**
     * 校验器名称数组，将会校验反射的执行结果
     */
    String[] validator() default {};

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
