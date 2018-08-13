package io.github.shenbinglife.validators.ex;

import io.github.shenbinglife.common.base.exception.UncheckedCodeException;

/**
 * 校验失败异常
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class ValidateException extends UncheckedCodeException {

    public ValidateException(String message, int code) {
        super(message, code);
    }
}
