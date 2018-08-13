package io.github.shenbinglife.validators.validators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.ValidatorRegistry;
import io.github.shenbinglife.validators.anno.cfg.Each;
import io.github.shenbinglife.validators.ex.NoSuchValidatorException;
import io.github.shenbinglife.validators.util.ValidateUtils;

/**
 * 容器元素内部校验
 *
 * @author shenbing
 * @version 2018/2/4
 * @since since
 */
public class EachValidator implements ComplexValidator<Object, Each> {

    @Override
    public boolean validate(Object object, Each annotation, ValidateContext context) {
        if (object == null) {
            return true;
        }

        ValidatorRegistry registry = context.getChecker().getRegistry();

        List<ComplexValidator> validators = new ArrayList<>();
        for (String validatorName : annotation.value()) {
            if (!registry.containsValidator(validatorName)) {
                throw new NoSuchValidatorException("尝试使用一个不存在的校验器：" + validatorName);
            }
            validators.add(registry.getValidator(validatorName));
        }
        for (Class<? extends ComplexValidator> clazz : annotation.classes()) {
            if (!registry.containsValidator(clazz)) {
                throw new NoSuchValidatorException("尝试使用一个不存在的校验器：" + clazz.getName());
            }
            validators.add(registry.getValidator(clazz));
        }


        if (ValidateUtils.isArray(object)) {
            for (Object item : (Object[]) object) {
                if (!fastValidate(validators, item, context)) {
                    return false;
                }
            }

        } else if (ValidateUtils.isCollection(object)) {
            for (Object item : (Collection<?>) object) {
                if (!fastValidate(validators, item, context)) {
                    return false;
                }
            }
        } else if (ValidateUtils.isMap(object)) {
            for (Object item : ((Map) object).values()) {
                if (!fastValidate(validators, item, context)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 快速执行多个校验器，一旦有一个校验不通过，则返回false
     * 
     * @param validators 校验器集合
     * @param object 校验对象
     * @param context 校验上下文
     * @return 校验结果
     */
    private boolean fastValidate(List<ComplexValidator> validators, Object object, ValidateContext context) {
        for (ComplexValidator validator : validators) {
            if (!validator.validate(object, null, context)) {
                return false;
            }
        }
        return true;
    }
}
