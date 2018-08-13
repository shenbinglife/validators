package io.github.shenbinglife.validators.validators;


import java.util.regex.Pattern;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.Regex;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class RegexValidator implements ComplexValidator<String, Regex> {


    @Override
    public boolean validate(String object, Regex regexValidate, ValidateContext context) {
        if (object == null) {
            return true;
        }
        if (regexValidate.zeroAble() && object.length() == 0) {
            return true;
        }

        Pattern pattern = Pattern.compile(regexValidate.pattern());
        return pattern.matcher(object).matches();
    }
}
