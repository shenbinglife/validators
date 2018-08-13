package io.github.shenbinglife.validators.validators;// package com.trendytech.common.validator.validators;

//
// import java.lang.annotation.Annotation;
// import java.lang.reflect.Field;
//
// import com.trendytech.common.validator.SimpleValidateResult;
// import org.apache.commons.lang3.ClassUtils;
//
// import com.trendytech.common.base.exception.UncheckedException;
// import com.trendytech.common.validator.ComplexValidator;
// import com.trendytech.common.validator.AbstractValidateChecker;
// import com.trendytech.common.validator.ValidateContext;
// import com.trendytech.common.validator.anno.meta.Validate;
// import com.trendytech.common.validator.ex.ValidateException;
//
/// **
// * 对象内部校验器
// *
// * @author shenbing
// * @version 2017/11/20
// * @since since
// */
// public class ValidateValidator implements ComplexValidator<Object, Validate> {
//
// @Override
// public boolean validate(Object object, Validate annotation, ValidateContext context) {
// if (object == null) {
// return true;
// } else if (ClassUtils.isPrimitiveOrWrapper(object.getClass())) {
// return true;
// } else {
// return this.validateFields(object, context);
// }
// }
//
// private boolean validateFields(Object object, ValidateContext context) throws ValidateException {
// AbstractValidateChecker checker = context.getChecker();
// Class<?> aClass = object.getClass();
// Field[] fields = aClass.getDeclaredFields();
// try {
// for (Field field : fields) {
// field.setAccessible(true);
// Object fieldValue = field.get(object);
// Annotation[] annotations = field.getAnnotations();
// for (Annotation annotation : annotations) {
// if (checker.isValidateAnnotation(annotation)) {
// SimpleValidateResult simpleValidateResult = checker.checkObject(fieldValue, annotation);
// if(!simpleValidateResult.totalSuccess()) {
// throw simpleValidateResult.resolveException(context);
// }
// }
// }
// }
// return true;
// } catch (IllegalAccessException e) {
// throw new UncheckedException(e.getMessage(), e);
// }
// }
// }
