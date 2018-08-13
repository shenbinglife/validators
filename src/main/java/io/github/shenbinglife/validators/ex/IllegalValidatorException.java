package io.github.shenbinglife.validators.ex;

import io.github.shenbinglife.common.base.exception.UncheckedException;

/**
 * 非法的校验器异常
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class IllegalValidatorException extends UncheckedException {

    public IllegalValidatorException(String message) {
        super(message);
    }

    public IllegalValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
