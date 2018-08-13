package io.github.shenbinglife.validators;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.shenbinglife.common.base.exception.UncheckedException;
import io.github.shenbinglife.validators.anno.meta.ValidateInside;
import io.github.shenbinglife.validators.ex.NoSuchValidatorException;
import io.github.shenbinglife.validators.ex.ValidateException;
import io.github.shenbinglife.validators.util.ValidateUtils;

/**
 * 默认提供的校验器
 *
 * @author shenbing
 * @version 2018/2/4
 * @since since
 */
public class DefaultValidateChecker implements ValidateChecker {

    private Logger logger = LoggerFactory.getLogger(DefaultValidateChecker.class);

    private ValidatorRegistry registry;

    public DefaultValidateChecker(ValidatorRegistry registry) {
        this.registry = registry;
    }

    @Override
    public ValidateCollector checkObject(ValidatedTarget object, ValidatorProperty validatorProperty)
                    throws ValidateException {
        ValidateCollector collector = new ValidateCollector(object);
        ValidateContext context = object.getContext();

        if (ValidateUtils.isSupportGroups(validatorProperty.getGroups(), context.getGroups())) {
            ValidateResult result = doCheckObject(object, validatorProperty);
            collector.collect(result);
        }

        List<ValidatorProperty> parentValidators = validatorProperty.getParentValidators();
        for (ValidatorProperty p : parentValidators) {
            ValidateResult result = doCheckObject(object, validatorProperty);
            collector.collect(result);
        }

        return collector;
    }

    @Override
    public ValidateCollector checkInside(ValidatedTarget target) {
        ValidateCollector collector = new ValidateCollector(target);
        try {
            Object object = target.getTarget();
            if (object != null) {
                Field[] allFields = FieldUtils.getAllFields(object.getClass());
                for (Field field : allFields) {
                    Object fieldValue = FieldUtils.readField(field, object, true);
                    Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                    ValidatedTarget fieldValidateTarget =
                                    new ValidatedTarget(fieldValue, declaredAnnotations, target.getContext());

                    if (fieldValue != null && ValidateUtils.isCollection(fieldValue)
                                    && hasValidateInsideAnnotation(declaredAnnotations)) {
                        ValidateCollector subInside = doCheckCollectionInside(fieldValidateTarget);
                        collector.collect(subInside);
                    } else if (fieldValue != null && ValidateUtils.isArray(fieldValue)
                                    && hasValidateInsideAnnotation(declaredAnnotations)) {
                        ValidateCollector subInside = doCheckArrayInside(fieldValidateTarget);
                        collector.collect(subInside);
                    }

                    if (fieldValidateTarget.getValidators().isEmpty()) {
                        continue;
                    }
                    ValidateCollector sub = fieldValidateTarget.check();
                    collector.collect(sub);
                }
            } else {
                logger.debug("当前被校验的对象为null， 忽略校验对象内部字段: {}", target);
            }
        } catch (IllegalAccessException e) {
            throw new UncheckedException("无法校验指定字段", e);
        }
        return collector;
    }

    private boolean hasValidateInsideAnnotation(Annotation[] declaredAnnotations) {
        return Arrays.stream(declaredAnnotations).anyMatch(an -> an instanceof ValidateInside);
    }

    private ValidateCollector doCheckArrayInside(ValidatedTarget arrayTarget) {
        ValidateCollector collector = new ValidateCollector(arrayTarget);
        Object[] array = (Object[]) arrayTarget.getTarget();
        for (Object object : array) {
            collector.collect(checkInside(new ValidatedTarget(object, arrayTarget.getContext())));
        }
        return collector;
    }

    private ValidateCollector doCheckCollectionInside(ValidatedTarget collectionTarget) {
        ValidateCollector collector = new ValidateCollector(collectionTarget);
        Collection<?> collection = (Collection<?>) collectionTarget.getTarget();
        for (Object item : collection) {
            collector.collect(checkInside(new ValidatedTarget(item, collectionTarget.getContext())));
        }
        return collector;
    }

    /**
     * 根据校验器属性校验对象
     * 
     * @param target 被校验的对象
     * @param property 校验器属性
     * @return 校验结果
     */
    private ValidateResult doCheckObject(ValidatedTarget target, ValidatorProperty property) {
        ComplexValidator validator = registry.getValidator(property.getValidatorName(), property.getValidatorClass());
        if (validator == null) {
            throw new NoSuchValidatorException(String.format("无法找到指定的校验器, name:%s, class:%s",
                            property.getValidatorName(),
                            property.getValidatorClass() == null ? "null" : property.getValidatorClass().getName()));
        }
        Object validatedTarget = target.getTarget();
        if (validatedTarget != null && property.isArrayCheck() && ValidateUtils.isArray(validatedTarget)) {
            return doCheckArrayObject(target, property);
        } else if (validatedTarget != null && property.isArrayCheck() && ValidateUtils.isCollection(validatedTarget)) {
            return doCheckCollection(target, property);
        } else {
            boolean result = validator.validate(validatedTarget, property.getOriginAnnotation(), target.getContext());
            if (!result && target.getContext().isFastFailed()) {
                throw ValidateUtils.resolveException(property, target.getContext());
            }
            return new SimpleValidateResult(target, property, result);
        }
    }

    /**
     * 校验集合对象元素
     * 
     * @param target 被校验对象
     * @param property 校验器属性
     * @return 校验结果
     */
    private ValidateCollector doCheckCollection(ValidatedTarget target, ValidatorProperty property) {
        ValidateCollector collector = new ValidateCollector(target);
        Collection<?> collection = (Collection<?>) target.getTarget();
        for (Object item : collection) {
            ValidatedTarget itemTarget = new ValidatedTarget(item, new Annotation[] {property.getOriginAnnotation()},
                            target.getContext());
            ValidateCollector checked = itemTarget.check();
            collector.collect(checked);
        }

        return collector;
    }

    /**
     * 校验数组对象元素
     * 
     * @param target 被校验对象
     * @param property 校验器属性
     * @return 校验结果
     */
    private ValidateCollector doCheckArrayObject(ValidatedTarget target, ValidatorProperty property) {
        ValidateCollector collector = new ValidateCollector(target);
        Object[] array = (Object[]) target.getTarget();
        for (int i = 0; i < array.length; i++) {
            ValidatedTarget itemTarget = new ValidatedTarget(array[i],
                            new Annotation[] {property.getOriginAnnotation()}, target.getContext());
            ValidateCollector checked = itemTarget.check();
            collector.collect(checked);
        }
        return collector;
    }

    @Override
    public ValidatorRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(ValidatorRegistry registry) {
        this.registry = registry;
    }
}
