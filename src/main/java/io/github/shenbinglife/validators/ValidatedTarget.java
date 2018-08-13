package io.github.shenbinglife.validators;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.shenbinglife.validators.anno.meta.Groups;
import io.github.shenbinglife.validators.anno.meta.ValidateEx;
import io.github.shenbinglife.validators.anno.meta.ValidateInside;
import io.github.shenbinglife.validators.util.ValidateUtils;

/**
 * 被校验对象
 * <P>
 * 值得注意: 当被校验对象为null时，无法获取到对象的Class，所以不会执行对象的Class上标记的任何校验注解
 * </P>
 *
 * @author shenbing
 * @version 2018/2/4
 * @since since
 */
public class ValidatedTarget {

    private Logger logger = LoggerFactory.getLogger(ValidatedTarget.class);

    private Object target;

    private List<ValidatorProperty> validators;

    private ValidateContext context;

    /**
     * 被校验对象
     * <P>
     * 内部使用一个默认的校验器上下文
     * </P>
     * 
     * @param target 被校验的原始对象
     */
    public ValidatedTarget(Object target) {
        this.target = target;
        this.context = resolveContext(ValidateContext.newInstance(), new Annotation[0], target);
        this.validators = new ArrayList<>();
    }

    /**
     * 被校验对象
     * 
     * @param target 被校验的原始对象
     * @param parentContext 父级校验上下文，当前校验环境会继承所有父级上下文信息，除了是否校验对象内部的属性
     */
    public ValidatedTarget(Object target, ValidateContext parentContext) {
        this.target = target;
        this.context = resolveContext(parentContext.copied(), new Annotation[0], target);
        this.validators = new ArrayList<>();
    }

    /**
     * 被校验对象
     * <P>
     * 内部使用一个默认的校验器上下文
     * </P>
     * 
     * @param target 被校验的原始对象
     * @param annotations 被校验对象上的所有注解
     */
    public ValidatedTarget(Object target, Annotation[] annotations) {
        this.target = target;
        this.context = resolveContext(ValidateContext.newInstance(), annotations, target);
        this.validators = resolveValidators(annotations, target);
    }

    /**
     * 被校验对象
     * 
     * @param target 被校验的原始对象
     * @param annotations 被校验对象上的所有注解
     * @param parentContext 父级校验上下文，当前校验环境会继承所有父级上下文信息，除了是否校验对象内部的属性
     */
    public ValidatedTarget(Object target, Annotation[] annotations, ValidateContext parentContext) {
        this.target = target;
        this.context = resolveContext(parentContext.copied(), annotations, target);
        this.validators = resolveValidators(annotations, target);
    }

    /**
     * 根据对象注解解析校验器
     * 
     * @param annotations
     * @return
     */
    private List<ValidatorProperty> resolveValidators(Annotation[] annotations, Object target) {
        List<ValidatorProperty> validatorProperties = new ArrayList<>(16);
        for (Annotation annotation : annotations) {
            if (ValidateUtils.isValidateorAnnotation(annotation)) {
                ValidatorProperty property = ValidateUtils.buildValidatorProperty(annotation, target);
                validatorProperties.add(property);
            }
        }

        if (target != null) {
            Class<?> aClass = target.getClass();
            List<Annotation> classValidateAnnotations = ValidateUtils.getValidateAnnotations(aClass);
            for (Annotation annotation : classValidateAnnotations) {
                ValidatorProperty property = ValidateUtils.buildValidatorProperty(annotation, target);
                validatorProperties.add(property);
            }
        } else {
            logger.debug("当前被校验的对象为null， 忽略注册对象类型上的校验器");
        }
        return validatorProperties;
    }

    /**
     * 根据对象注解配置校验上下文
     * 
     * @param context
     * @param annotations
     * @return
     */
    private ValidateContext resolveContext(ValidateContext context, Annotation[] annotations, Object target) {
        if (target != null) {
            Class<?> aClass = target.getClass();
            ValidateInside validateInside = aClass.getAnnotation(ValidateInside.class);
            if (validateInside != null) {
                context.setValidateInside(true);
            }
        } else {
            logger.debug("当前被校验的对象为null， 忽略配置对象类型上的校验上下文信息");
        }

        for (Annotation annotation : annotations) {
            if (annotation instanceof Groups) {
                context.addGroups(((Groups) annotation).value());
            } else if (annotation instanceof ValidateEx) {
                context.setGlobalValidateException(((ValidateEx) annotation).value());
            } else if (annotation instanceof ValidateInside) {
                context.setValidateInside(true);
            }
        }
        return context;
    }

    /**
     * 强制启用校验对象内部字段的功能
     */
    public void forceValidateInside() {
        this.context.setValidateInside(true);
    }

    /**
     * 执行校验
     *
     * <P>
     * 如果校验环境设置了快速失败的属性为true，那么一旦出现校验失败，则会抛出异常
     * </P>
     * 
     * @return 校验结果收集器
     */
    public ValidateCollector check() {
        ValidateCollector collector = new ValidateCollector(this);
        ValidateChecker checker = context.getChecker();
        for (ValidatorProperty p : validators) {
            ValidateCollector result = checker.checkObject(this, p);
            collector.collect(result);
        }
        if (context.isValidateInside()) {
            ValidateCollector result = checker.checkInside(this);
            collector.collect(result);
        }
        return collector;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<ValidatorProperty> getValidators() {
        return validators;
    }

    public void setValidators(List<ValidatorProperty> validators) {
        this.validators = validators;
    }

    public ValidateContext getContext() {
        return context;
    }

    public void setContext(ValidateContext context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "ValidatedTarget{" + "target=" + target + ", validators=" + validators + ", context=" + context + '}';
    }
}
