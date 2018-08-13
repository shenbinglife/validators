package io.github.shenbinglife.validators.anno.meta;

import java.lang.annotation.*;

/**
 * 检查是否为数组或集合
 *
 * <P>
 * 在任意校验注解类型加上{@code @ArrayCheck}注解， 会在运行时对该校验对象的类型进行判断<br>
 * 如果是数组或者集合对象，则会对内部元素执行当前所有其他的校验器将， 否则执行对校验对象执行校验器
 * </P>
 *
 * @author shenbing
 * @version 2017/11/17
 * @since 1.0.0
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArrayCheck {

}
