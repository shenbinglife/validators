package io.github.shenbinglife.validators;

import java.util.HashMap;
import java.util.Map;

import io.github.shenbinglife.validators.ex.IllegalValidatorException;

/**
 * 校验器注册中心
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class ValidatorRegistry {

    protected Map<String, ComplexValidator> validatorMap = new HashMap<>();

    protected Map<Class<? extends ComplexValidator>, ComplexValidator> classMap = new HashMap<>();

    /**
     * 注册校验器
     * 
     * @param name 校验器名称
     * @param complexValidator 校验器对象
     */
    public void register(String name, ComplexValidator complexValidator) {
        if (validatorMap.containsKey(name)) {
            throw new IllegalValidatorException("重复注册同名称的校验器：" + name);
        }
        Class<? extends ComplexValidator> validatorClass = complexValidator.getClass();
        if (classMap.containsKey(validatorClass)) {
            throw new IllegalValidatorException("重复注册同类型的校验器：" + validatorClass);
        }
        validatorMap.put(name, complexValidator);
        classMap.put(validatorClass, complexValidator);
    }

    /**
     * 是否包含指定名称的校验器
     * 
     * @param validatorName 校验器名称
     * @return true：包含， false：不包含
     */
    public boolean containsValidator(String validatorName) {
        return this.validatorMap.containsKey(validatorName);
    }

    /**
     * 根据校验器名称获取校验器
     * 
     * @param validatorName 校验器名称
     * @return 校验器对象，找不到时返回null
     */
    public ComplexValidator getValidator(String validatorName) {
        return this.validatorMap.get(validatorName);
    }

    /**
     * 是否包含指定类型的校验器
     * 
     * @param clazz 校验器类型
     * @return true：包含， false：不包含
     */
    public boolean containsValidator(Class<? extends ComplexValidator> clazz) {
        return this.classMap.containsKey(clazz);
    }

    /**
     * 根据校验器类型获取校验器
     * 
     * @param clazz 校验器类型
     * @return 校验器对象，找不到时返回null
     */
    public ComplexValidator getValidator(Class<? extends ComplexValidator> clazz) {
        return this.classMap.get(clazz);
    }

    /**
     * 优先根据校验器名称获取校验器，找不到时，根据类型获取校验器对象。
     * 
     * @param name 校验器名称
     * @param clazz 校验器类型
     * @return 校验器对象，找不到时返回null
     */
    public ComplexValidator getValidator(String name, Class<? extends ComplexValidator> clazz) {
        ComplexValidator validator = this.getValidator(name);
        if (validator == null) {
            validator = this.getValidator(clazz);
        }
        return validator;
    }

    public Map<String, ComplexValidator> getValidatorMap() {
        return validatorMap;
    }

    public Map<Class<? extends ComplexValidator>, ComplexValidator> getClassMap() {
        return classMap;
    }
}
