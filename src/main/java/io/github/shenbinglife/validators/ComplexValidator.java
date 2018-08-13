package io.github.shenbinglife.validators;

/**
 * 校验器接口
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
@FunctionalInterface
public interface ComplexValidator<T, K> {

    /**
     * 将Validator转为ComplexValidator
     *
     * @param validator Validator对象
     * @param <T> Validator泛型
     * @return ComplexValidator对象
     */
    static <T> ComplexValidator<T, ?> of(Validator<T> validator) {
        return (object, annotation, context) -> validator.validate(object, context);
    }

    /**
     * 根据校验器，创建相对立的一个校验器
     *
     * @param validator 校验器
     * @param <T> 校验对象泛型
     * @param <K> 校验器注解泛型
     * @return 新的校验器，永远与传入参数的校验器的校验结果相反
     */
    static <T, K> ComplexValidator<T, K> not(ComplexValidator<T, K> validator) {
        return (object, anno, context) -> !validator.validate(object, anno, context);
    }

    /**
     * 校验对象
     *
     * @param object 被校验的对象
     * @param annotation 被校验对象的注解
     * @return 校验结果，true：校验通过
     */
    boolean validate(T object, K annotation, ValidateContext context);
}
