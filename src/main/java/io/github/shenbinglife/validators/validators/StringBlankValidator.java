package io.github.shenbinglife.validators.validators;


import org.apache.commons.lang3.StringUtils;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.Validator;
import io.github.shenbinglife.validators.anno.cfg.Blank;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class StringBlankValidator implements Validator<String>, ComplexValidator<String, Blank> {
    @Override
    public boolean validate(String object, ValidateContext context) {
        return StringUtils.isBlank(object);
    }

    @Override
    public boolean validate(String object, Blank annotation, ValidateContext context) {
        return validate(object, context);
    }
}
