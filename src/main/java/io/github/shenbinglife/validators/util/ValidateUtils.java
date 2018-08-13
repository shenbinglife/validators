package io.github.shenbinglife.validators.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.shenbinglife.common.base.util.Assert;
import io.github.shenbinglife.validators.Consts;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.ValidatorProperty;
import io.github.shenbinglife.validators.anno.meta.ArrayCheck;
import io.github.shenbinglife.validators.anno.meta.ComplexValidateMeta;
import io.github.shenbinglife.validators.anno.meta.MsgFiller;
import io.github.shenbinglife.validators.anno.meta.ValidateEx;
import io.github.shenbinglife.validators.ex.IllegalValidateExException;
import io.github.shenbinglife.validators.ex.IllegalValidatorException;
import io.github.shenbinglife.validators.ex.ValidateException;

/**
 * 校验工具类
 *
 * @author shenbing
 * @version 2018/2/4
 * @since since
 */
public class ValidateUtils {

    /**
     * 是否为校验器注解
     * 
     * @param annotation 注解
     */
    public static boolean isValidateorAnnotation(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        return annotationType.getAnnotation(ComplexValidateMeta.class) != null;
    }

    /**
     * 创建校验器属性对象
     *
     * @param annotation 注解
     * @param object 对象
     * @return 校验器属性对象
     */
    public static ValidatorProperty buildValidatorProperty(Annotation annotation, Object object) {
        Assert.isTrue(isValidateorAnnotation(annotation), "尝试从非校验注解上获取信息:" + annotation);

        ValidatorProperty property = new ValidatorProperty();
        Class<? extends Annotation> annotationType = annotation.annotationType();
        try {
            String[] groups = (String[]) annotationType.getMethod(ValidatorMethods.GROUPS).invoke(annotation);
            String emsg = (String) annotationType.getMethod(ValidatorMethods.EMSG).invoke(annotation);
            int ecode = (int) annotationType.getMethod(ValidatorMethods.ECODE).invoke(annotation);
            String field = (String) annotationType.getMethod(ValidatorMethods.FIELD).invoke(annotation);

            property.setOriginAnnotation(annotation);
            property.setEmsg(emsg);
            property.setGroups(groups);
            property.setField(field);
            property.setEcode(ecode);
            property.addFormatParam(Consts.MessageFillParams.FIELD, field);
            if (object != null && object.getClass().isArray()) {
                property.addFormatParam(Consts.MessageFillParams.VAL, Arrays.toString((Object[]) object));
            } else {
                property.addFormatParam(Consts.MessageFillParams.VAL, String.valueOf(object));
            }

            Method[] declaredMethods = annotationType.getDeclaredMethods();
            for (Method m : declaredMethods) {
                MsgFiller msgFiller = m.getAnnotation(MsgFiller.class);
                if (msgFiller != null) {
                    Class<?> returnType = m.getReturnType();
                    Object invoke = m.invoke(annotation);
                    if (returnType.isArray()) {
                        property.addFormatParam(msgFiller.value(), Arrays.toString((Object[]) invoke));
                    } else {
                        property.addFormatParam(msgFiller.value(), invoke);
                    }
                }
            }

            Annotation[] parentAnnos = annotationType.getAnnotations();
            for (Annotation anno : parentAnnos) {
                if (isValidateorAnnotation(anno)) {
                    property.addParentValidatorProperty(buildValidatorProperty(anno, object));
                } else if (anno instanceof ArrayCheck) {
                    property.setArrayCheck(true);
                } else if (anno instanceof ComplexValidateMeta) {
                    property.setValidatorClass(((ComplexValidateMeta) anno).clazz());
                    property.setValidatorName(((ComplexValidateMeta) anno).value());
                } else if (anno instanceof ValidateEx) {
                    property.setValidateException(((ValidateEx) anno).value());
                }
            }
            if (property.getValidatorClass() == null || property.getValidatorName() == null) {
                throw new IllegalValidatorException(
                                "非法的校验注解，没有使用ComplexValidatorMeta元注解表示校验器:" + annotationType.getName());
            }
            return property;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalValidatorException(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new IllegalValidatorException("非法的校验注解，没有定义通用的校验属性:" + annotationType.getName(), e);
        }
    }

    /**
     * 判断校验组是否符合当前全局校验组范围
     *
     * @param groups 当前校验组
     * @param environmentGroups 校验环境中校验组属性
     * @return true：当前校验组中为空，或任意一个组环境存在于校验环境中
     */
    public static boolean isSupportGroups(String[] groups, List<String> environmentGroups) {
        if (groups == null || groups.length == 0) {
            return true;
        } else {
            if (environmentGroups == null || environmentGroups.isEmpty()) {
                return false;
            } else {
                return Arrays.stream(groups)
                                .anyMatch(neededGroup -> environmentGroups.stream().anyMatch(neededGroup::equals));
            }
        }
    }

    /**
     * 解析校验异常
     * 
     * @param property 校验器属性
     * @param context 校验上下文
     * @return 最终确定的错误码
     */
    public static ValidateException resolveException(ValidatorProperty property, ValidateContext context) {
        Class<? extends ValidateException> clazz = context.getGlobalValidateException();
        clazz = clazz == null ? property.getValidateException() : clazz;
        int ecode = resolveEcode(property, context);
        if (clazz == null) {
            return new ValidateException(property.getFormattedMsg(), ecode);
        } else {
            try {
                Constructor<? extends ValidateException> constructor = clazz.getConstructor(String.class, int.class);
                return constructor.newInstance(property.getFormattedMsg(), ecode);
            } catch (NoSuchMethodException e) {
                throw new IllegalValidateExException("非法的自定义校验异常, 没有指定的构造方法: constructor(String, int)");
            } catch (IllegalAccessException e) {
                throw new IllegalValidateExException("无法访问自定义校验异常构造方法");
            } catch (InstantiationException e) {
                throw new IllegalValidateExException("反射构建自定义校验异常失败");
            } catch (InvocationTargetException e) {
                throw new IllegalValidateExException("反射构建自定义校验异常失败");
            }
        }
    }

    /**
     * 解析校验错误码
     * 
     * @param property 校验器属性
     * @param context
     * @return 最终确定的错误码
     */
    public static int resolveEcode(ValidatorProperty property, ValidateContext context) {
        int propertyEcode = property.getEcode();
        int globalEcode = context.getGlobalEcode();
        return propertyEcode == Consts.DEFAULT_ECODE ? globalEcode : propertyEcode;
    }

    public static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    public static boolean isCollection(Object object) {
        return Collection.class.isAssignableFrom(object.getClass());
    }

    public static boolean isMap(Object object) {
        return Map.class.isAssignableFrom(object.getClass());
    }

    public static List<Annotation> getValidateAnnotations(Class<?> aClass) {
        Annotation[] annotations = aClass.getAnnotations();
        return Arrays.stream(annotations).filter(ValidateUtils::isValidateorAnnotation).collect(Collectors.toList());
    }
}
