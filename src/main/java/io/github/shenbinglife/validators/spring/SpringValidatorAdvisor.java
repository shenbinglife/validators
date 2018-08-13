package io.github.shenbinglife.validators.spring;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.shenbinglife.validators.*;
import io.github.shenbinglife.validators.anno.meta.Groups;

/**
 * 切面拦截器
 *
 * @author shenbing
 * @version 2017/11/20
 * @since since
 */
@Aspect
public class SpringValidatorAdvisor {

    private final Logger logger = LoggerFactory.getLogger(SpringValidatorAdvisor.class);

    @Autowired
    private ValidatorRegistry registry;

    private int defaultEcode = Consts.DEFAULT_ECODE;

    /**
     * 快速失败
     * <P>
     * true: 表示如果参数一旦校验，立刻抛出校验失败异常
     * </P>
     * <P>
     * false: 即使存在参数校验失败，也必须等到该参数所有的校验器执行后，才会抛出异常
     * </P>
     */
    private boolean fastFailed = true;

    @Around("@within(io.github.shenbinglife.validators.anno.meta.Validators) || @annotation(io.github.shenbinglife.validators.anno.meta.Validators)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(method.getName(),
                                method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                logger.info("无法在实现类中找到指定的方法，所以无法实现校验器验证，method：" + method.getName());
                return joinPoint.proceed();
            }
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        ValidateContext rootValidateContext = this.resolveContext(method);

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Object parameter = joinPoint.getArgs()[i];
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            ValidatedTarget validatedTarget = new ValidatedTarget(parameter, parameterAnnotation, rootValidateContext);
            validatedTarget.check();
        }
        return joinPoint.proceed();
    }

    private ValidateContext resolveContext(Method method) {
        ValidateContext context = new ValidateContext();
        context.setChecker(new DefaultValidateChecker(this.registry));
        context.setGlobalEcode(this.defaultEcode);
        context.setFastFailed(this.fastFailed);
        Groups groups = method.getAnnotation(Groups.class);
        if (groups != null) {
            context.addGroups(groups.value());
        }
        return context;
    }

    public void setRegistry(ValidatorRegistry registry) {
        this.registry = registry;
    }

    public void setDefaultEcode(int defaultEcode) {
        this.defaultEcode = defaultEcode;
    }

    public void setFastFailed(boolean fastFailed) {
        this.fastFailed = fastFailed;
    }
}
