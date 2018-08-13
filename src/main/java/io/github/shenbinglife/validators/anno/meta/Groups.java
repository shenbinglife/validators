package io.github.shenbinglife.validators.anno.meta;

import java.lang.annotation.*;

/**
 * 校验组注解，标记当前位置被激活的校验组
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Groups {

    /**
     * 表示被激活的校验组
     */
    String[] value() default {};
}
