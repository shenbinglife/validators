package io.github.shenbinglife.validators.ex;

import io.github.shenbinglife.common.base.exception.UncheckedException;

/**
 * 无法找到校验器
 *
 * @author shenbing
 * @version 2017/11/21
 * @since since
 */
public class NoSuchValidatorException extends UncheckedException {

    public NoSuchValidatorException() {}

    public NoSuchValidatorException(String message) {
        super(message);
    }

    public NoSuchValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchValidatorException(Throwable cause) {
        super(cause);
    }
}
