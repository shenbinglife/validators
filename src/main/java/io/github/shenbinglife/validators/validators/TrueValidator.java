package io.github.shenbinglife.validators.validators;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.True;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class TrueValidator implements ComplexValidator<Boolean, True> {
    @Override
    public boolean validate(Boolean object, True annotation, ValidateContext context) {
        if (object == null) {
            return annotation.nullable();
        }
        return object;
    }
}
