package io.github.shenbinglife.validators.anno.meta;

import java.lang.annotation.*;

/**
 * 标记注解，标明要被拦截的类或方法
 *
 * @author shenbing
 * @version 2017/11/21
 * @since since
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validators {
}
