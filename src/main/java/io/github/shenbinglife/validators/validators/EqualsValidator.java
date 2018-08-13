package io.github.shenbinglife.validators.validators;

import java.util.Objects;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.Equals;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class EqualsValidator implements ComplexValidator<String, Equals> {

    @Override
    public boolean validate(String object, Equals annotation, ValidateContext context) {
        return object == null || Objects.equals(object, annotation.value());
    }
}
