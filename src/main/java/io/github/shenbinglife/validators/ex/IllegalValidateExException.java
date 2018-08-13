package io.github.shenbinglife.validators.ex;

import io.github.shenbinglife.common.base.exception.UncheckedException;

/**
 * 非法的自定义校验异常
 *
 * @author shenbing
 * @version 2017/11/24
 * @since since
 */
public class IllegalValidateExException extends UncheckedException {

    public IllegalValidateExException(String message) {
        super(message);
    }
}
