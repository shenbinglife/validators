package io.github.shenbinglife.validators.anno.meta;

import java.lang.annotation.*;

import io.github.shenbinglife.validators.ComplexValidator;

/**
 * 自定义校验注解元注解，在任意的注解定义上，增加该注解标明这是一个校验注解
 *
 * <P>
 * 在校验环境
 * </P>
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexValidateMeta {

    /**
     * 校验器名称， 优先使用类型匹配
     */
    String value() default "";

    /**
     * 校验器类， 优先使用类型匹配
     */
    Class<? extends ComplexValidator> clazz() default ComplexValidator.class;
}
