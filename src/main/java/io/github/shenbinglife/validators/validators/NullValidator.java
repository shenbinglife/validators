package io.github.shenbinglife.validators.validators;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.Validator;
import io.github.shenbinglife.validators.anno.cfg.Null;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class NullValidator implements Validator<Object>, ComplexValidator<Object, Null> {
    @Override
    public boolean validate(Object object, ValidateContext context) {
        return object == null;
    }

    @Override
    public boolean validate(Object object, Null annotation, ValidateContext context) {
        return validate(object, context);
    }
}
