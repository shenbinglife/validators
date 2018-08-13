package io.github.shenbinglife.validators.validators;


import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import io.github.shenbinglife.common.base.exception.UncheckedException;
import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.ValidatorRegistry;
import io.github.shenbinglife.validators.anno.cfg.Reflect;
import io.github.shenbinglife.validators.ex.IllegalValidatorException;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/16
 * @since since
 */
public class ReflectValidator implements ComplexValidator<Object, Reflect>, ApplicationContextAware {

    private ValidatorRegistry registry;

    private ApplicationContext applicationContext;

    public ReflectValidator(ValidatorRegistry registry) {
        this.registry = registry;
    }

    @Override
    public boolean validate(Object object, Reflect annotation, ValidateContext context) {
        if (object == null) {
            return true;
        }
        Class<?> targetClass = annotation.target();
        String methodName = annotation.method();
        Object result = null;
        try {
            Method method = targetClass.getDeclaredMethod(methodName, object.getClass());
            Object bean = applicationContext.getBean(targetClass);
            result = ReflectionUtils.invokeMethod(method, bean);
        } catch (NoSuchMethodException e) {
            throw new UncheckedException(e.getMessage(), e);
        }

        for (String name : annotation.validator()) {
            if (!registry.containsValidator(name)) {
                throw new IllegalValidatorException("尝试使用一个不存在的校验器：" + name);
            }
            if (!registry.getValidator(name).validate(result, null, context)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
