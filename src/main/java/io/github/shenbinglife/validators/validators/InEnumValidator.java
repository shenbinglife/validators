package io.github.shenbinglife.validators.validators;


import java.lang.reflect.Method;
import java.util.Objects;

import org.springframework.util.ReflectionUtils;

import io.github.shenbinglife.common.base.exception.UncheckedException;
import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.InEnum;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class InEnumValidator implements ComplexValidator<Object, InEnum> {
    @Override
    public boolean validate(Object object, InEnum annotation, ValidateContext context) {
        if (object == null) {
            return true;
        }
        Class<? extends Enum> enumClass = annotation.enumClass();
        try {
            Method method = enumClass.getMethod(annotation.method(), null);
            Enum[] enums = enumClass.getEnumConstants();
            for (Enum e : enums) {
                Object value = ReflectionUtils.invokeMethod(method, e);
                if (Objects.equals(value, object)) {
                    return true;
                }
            }
            return false;
        } catch (NoSuchMethodException e) {
            throw new UncheckedException(e.getMessage(), e);
        }

    }
}
