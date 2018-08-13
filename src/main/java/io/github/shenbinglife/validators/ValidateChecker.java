package io.github.shenbinglife.validators;

import io.github.shenbinglife.validators.ex.ValidateException;

/**
 * 校验检查器
 *
 * @author shenbing
 * @version 2018/2/4
 * @since since
 */
public interface ValidateChecker {

    /**
     * 根据指定的校验器校验对象
     * 
     * @param target 被校验对象
     * @param validator 校验器属性
     * @return 校验结果
     * @throws ValidateException 如果校验环境的fastFailed设置为true， 则校验失败时立刻抛出该异常
     */
    ValidateCollector checkObject(ValidatedTarget target, ValidatorProperty validator) throws ValidateException;

    /**
     * 校验对象内部的所有字段
     * 
     * @param target 被校验对象
     * @return 校验结果
     */
    ValidateCollector checkInside(ValidatedTarget target);

    /**
     * 获取校验检查器中使用的校验器注册中心对象
     * 
     * @return 校验器注册中心对象
     */
    ValidatorRegistry getRegistry();
}
