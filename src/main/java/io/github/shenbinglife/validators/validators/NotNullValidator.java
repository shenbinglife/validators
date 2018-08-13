package io.github.shenbinglife.validators.validators;


import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.NotNull;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class NotNullValidator implements ComplexValidator<Object, NotNull> {


    @Override
    public boolean validate(Object object, NotNull annotation, ValidateContext context) {
        return object != null;
    }
}
