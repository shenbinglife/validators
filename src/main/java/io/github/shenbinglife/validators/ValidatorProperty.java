package io.github.shenbinglife.validators;

import java.lang.annotation.Annotation;
import java.util.*;

import org.apache.commons.lang3.text.StrSubstitutor;

import io.github.shenbinglife.validators.ex.ValidateException;

/**
 * 校验注解所包含的通用属性
 *
 * @author shenbing
 * @version 2017/11/21
 * @since since
 */
public class ValidatorProperty {

    private Annotation originAnnotation;

    private List<ValidatorProperty> parentValidators;

    private boolean arrayCheck = false;

    private String[] groups;

    private String validatorName;

    private Class<? extends ComplexValidator> validatorClass;

    private int ecode;

    private String emsg;

    private Class<? extends ValidateException> validateException;

    private String field;

    private Map<String, Object> formatParams;

    public ValidatorProperty() {
        this.parentValidators = new ArrayList<>();
        this.formatParams = new HashMap<>();
    }

    /**
     * 添加父级校验注解属性
     */
    public void addParentValidatorProperty(ValidatorProperty property) {
        if (this.parentValidators == null) {
            this.parentValidators = new ArrayList<>();
        }
        this.parentValidators.add(property);
    }

    /**
     * 添加错误信息的字符串插值参数
     * 
     * @param name 插值名称
     * @param value 插值
     */
    public void addFormatParam(String name, Object value) {
        if (this.formatParams == null) {
            this.formatParams = new HashMap<>();
        }
        if (this.formatParams.containsKey(name)) {
            throw new IllegalArgumentException("当前异常信息格式化参数已经存在:" + name);
        }
        this.formatParams.put(name, value);
    }

    /**
     * 获取字符串插值后的验证信息
     */
    public String getFormattedMsg() {
        StrSubstitutor sub = new StrSubstitutor(formatParams);
        return sub.replace(this.emsg);
    }

    public Annotation getOriginAnnotation() {
        return originAnnotation;
    }

    public void setOriginAnnotation(Annotation originAnnotation) {
        this.originAnnotation = originAnnotation;
    }

    public List<ValidatorProperty> getParentValidators() {
        return parentValidators;
    }

    public void setParentValidators(List<ValidatorProperty> parentValidators) {
        this.parentValidators = parentValidators;
    }

    public boolean isArrayCheck() {
        return arrayCheck;
    }

    public void setArrayCheck(boolean arrayCheck) {
        this.arrayCheck = arrayCheck;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String getValidatorName() {
        return validatorName;
    }

    public void setValidatorName(String validatorName) {
        this.validatorName = validatorName;
    }

    public Class<? extends ComplexValidator> getValidatorClass() {
        return validatorClass;
    }

    public void setValidatorClass(Class<? extends ComplexValidator> validatorClass) {
        this.validatorClass = validatorClass;
    }

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public String getEmsg() {
        return emsg;
    }

    public void setEmsg(String emsg) {
        this.emsg = emsg;
    }

    public Class<? extends ValidateException> getValidateException() {
        return validateException;
    }

    public void setValidateException(Class<? extends ValidateException> validateException) {
        this.validateException = validateException;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Map<String, Object> getFormatParams() {
        return formatParams;
    }

    public void setFormatParams(Map<String, Object> formatParams) {
        this.formatParams = formatParams;
    }

    @Override
    public String toString() {
        return "ValidatorProperty{" + "parentValidators=" + parentValidators + ", arrayCheck=" + arrayCheck
                        + ", groups=" + Arrays.toString(groups) + ", validatorName='" + validatorName + '\''
                        + ", validatorClass=" + validatorClass + ", ecode=" + ecode + ", emsg='" + emsg + '\'' + '}';
    }
}
