package io.github.shenbinglife.validators.anno.meta;

import java.lang.annotation.*;

/**
 * 标记注解，是否校验对象内部。
 * <P>
 * 在方法参数或对象类型或字段上可以使用，表示遇到校验器遇到该对象时，会尝试校验对象内部的所有字段
 * </P>
 *
 * @author shenbing
 * @version 2017/11/17
 * @since 1.0.0
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateInside {

}
