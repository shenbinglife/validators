package io.github.shenbinglife.validators;

/**
 * 校验结果
 *
 * @author shenbing
 * @version 2018/2/5
 * @since since
 */
public interface ValidateResult {

    /**
     * 校验结果是否通过
     * 
     * @return true：校验通过
     */
    boolean isPass();

    /**
     * 获取被校验的对象
     * 
     * @return 被校验的对象
     */
    ValidatedTarget getTarget();
}
