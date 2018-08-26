package io.github.shenbinglife.validators.spring;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.SimpleValidatorRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/21
 * @since since
 */
public class SpringValidatorRegistry extends SimpleValidatorRegistry implements BeanPostProcessor {

    public SpringValidatorRegistry() {
        super();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ComplexValidator) {
            register(beanName, (ComplexValidator) bean);
        }
        return bean;
    }

}
