package io.github.shenbinglife.validators.validators;


import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;

/**
 * 这个校验器的结果永远为true
 *
 * @author shenbing
 * @version 2017/11/21
 * @since since
 */
public final class AlwaysTrue implements ComplexValidator {
    @Override
    public boolean validate(Object object, Object annotation, ValidateContext context) {
        return true;
    }
}
