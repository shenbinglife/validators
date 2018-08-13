package io.github.shenbinglife.validators.anno.meta;

import java.lang.annotation.*;

import io.github.shenbinglife.validators.ex.ValidateException;

/**
 * 校验异常注解，校验失败时将ValidateException替换为指定的异常并抛出.
 * <P>
 * 在被拦截方法的入参上使用，表明为全局校验异常.<br>
 * 在对象内部校验的字段上标记，表明为字段异常.<br>
 * 在校验器注解的定义上标记，表明为校验器异常.<br>
 *
 * <br>
 * 校验异常说明：<br>
 * 当校验失败时，如果定义了全局校验异常，则抛出全局校验异常；<br>
 * 然后判断如果定义了字段异常，则抛出字段异常；<br>
 * 最后判断如果定义了校验器注解异常，则抛出校验器注解上定义的异常;<br>
 * 如果都没定义，则抛出{@link ValidateException}
 * </P>
 *
 * @author shenbing
 * @version 2017/11/24
 * @since since
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateEx {
    /**
     * 异常类
     */
    Class<? extends ValidateException> value() default ValidateException.class;
}
