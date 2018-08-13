package io.github.shenbinglife.validators.validators;


import java.util.ArrayList;
import java.util.List;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.ValidatorRegistry;
import io.github.shenbinglife.validators.anno.cfg.MultiValidate;
import io.github.shenbinglife.validators.ex.NoSuchValidatorException;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class MultiValidator implements ComplexValidator<Object, MultiValidate> {

    @Override
    public boolean validate(Object object, MultiValidate multiValidate, ValidateContext context) {
        ValidatorRegistry registry = context.getChecker().getRegistry();

        List<ComplexValidator> validators = new ArrayList<>();
        for (String validatorName : multiValidate.value()) {
            if (!registry.containsValidator(validatorName)) {
                throw new NoSuchValidatorException("尝试使用一个不存在的校验器：" + validatorName);
            }
            validators.add(registry.getValidator(validatorName));
        }
        for (Class<? extends ComplexValidator> clazz : multiValidate.classes()) {
            if (!registry.containsValidator(clazz)) {
                throw new NoSuchValidatorException("尝试使用一个不存在的校验器：" + clazz.getName());
            }
            validators.add(registry.getValidator(clazz));
        }
        for (ComplexValidator validator : validators) {
            if (!validator.validate(object, null, context)) {
                return false;
            }
        }
        return true;
    }
}
