package io.github.shenbinglife.validators.validators;


import org.apache.commons.lang3.StringUtils;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.Validator;
import io.github.shenbinglife.validators.anno.cfg.NotBlank;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class StringNotBlankValidator implements Validator<String>, ComplexValidator<String, NotBlank> {

    @Override
    public boolean validate(String object, NotBlank annotation, ValidateContext context) {
        return validate(object, context);
    }

    @Override
    public boolean validate(String object, ValidateContext context) {
        return StringUtils.isNotBlank(object);
    }
}
