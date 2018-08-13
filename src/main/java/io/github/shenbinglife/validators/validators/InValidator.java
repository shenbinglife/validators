package io.github.shenbinglife.validators.validators;


import org.apache.commons.lang3.ArrayUtils;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.In;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class InValidator implements ComplexValidator<String, In> {
    @Override
    public boolean validate(String object, In annotation, ValidateContext context) {
        if (object == null) {
            return true;
        }
        return ArrayUtils.contains(annotation.value(), object);
    }
}
