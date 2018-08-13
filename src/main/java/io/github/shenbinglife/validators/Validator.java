package io.github.shenbinglife.validators;

/**
 * 校验器接口
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
@FunctionalInterface
public interface Validator<T> {

    /**
     * 根据校验器，创建相对立的一个校验器
     *
     * @param validator 校验器
     * @param <T> 校验对象泛型
     * @return 新的校验器，永远与传入参数的校验器的校验结果相反
     */
    static <T> Validator<T> not(Validator<T> validator) {
        return (object, context) -> !validator.validate(object, context);
    }

    /**
     * 校验对象
     *
     * @param object 被校验的对象
     * @param context 当前校验参数的上下文
     * @return 校验结果，true：校验通过
     */
    boolean validate(T object, ValidateContext context);
}
