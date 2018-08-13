package io.github.shenbinglife.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.shenbinglife.validators.ex.ValidateException;

/**
 * 当前校验的上下文信息
 *
 * @author shenbing
 * @version 2017/11/21
 * @since since
 */
public class ValidateContext {

    /**
     * 全局校验异常。
     * <P>
     * 当校验失败时，如果定义了全局校验异常，则抛出全局校验异常；
     * <P>
     * 然后判断如果定义了字段异常，则抛出字段异常；
     * <P>
     * 最后判断如果定义了校验器注解异常，则抛出校验器注解上定义的异常;
     * <P>
     * 如果都没定义，则抛出{@link ValidateException}
     */
    private Class<? extends ValidateException> globalValidateException;

    /**
     * 全局定义的错误码, 如果校验注解使用了-1 作为错误码，默认替换为该全局错误码
     */
    private int globalEcode = Consts.DEFAULT_ECODE;

    /**
     * 当前被激活的校验组
     */
    private List<String> groups = new ArrayList<>();

    /**
     * 校验检查器
     */
    private ValidateChecker checker;

    /**
     * 快速失败, 默认：true
     * <P>
     * true: 表示如果参数一旦校验，立刻抛出校验失败异常
     * </P>
     * <P>
     * false: 即使存在参数校验失败，也必须等到该参数所有的校验器执行后，才会抛出异常
     * </P>
     */
    private boolean fastFailed = true;

    /**
     * 是否校验对象内部, 默认：false
     */
    private boolean validateInside = false;

    public ValidateContext() {}

    /**
     * 提供一个包含默认校验器注册中心的校验器上下文
     * 
     * @return 校验器上下文对象
     */
    public static ValidateContext newInstance() {
        ValidateContext context = new ValidateContext();
        context.setChecker(new DefaultValidateChecker(new SimpleValidatorRegistry()));
        return context;
    }

    /**
     * 根据当前上下文环境，生成一个新的校验环境对象
     * <P>
     * 1. 这两个上下文对象的修改互相不会影响
     * </P>
     * <P>
     * 2. 仅仅{@code validateInside}属性没有传递，其他属性都有传递
     * </P>
     * 
     * @return 新的校验器上下文
     */
    public ValidateContext copied() {
        ValidateContext copy = new ValidateContext();
        copy.setGlobalEcode(this.globalEcode);
        copy.setChecker(this.checker);
        copy.setGroups(new ArrayList<>(this.groups));
        copy.setFastFailed(this.fastFailed);
        copy.setGlobalValidateException(this.globalValidateException);
        return copy;
    }

    /**
     * 添加校验组
     * 
     * @param groups 校验组
     */
    public void addGroups(String... groups) {
        if (groups == null || groups.length == 0) {
            return;
        }
        if (this.groups == null) {
            this.groups = new ArrayList<>();
        }
        this.groups.addAll(Arrays.asList(groups));
    }

    public Class<? extends ValidateException> getGlobalValidateException() {
        return globalValidateException;
    }

    public void setGlobalValidateException(Class<? extends ValidateException> globalValidateException) {
        this.globalValidateException = globalValidateException;
    }

    public int getGlobalEcode() {
        return globalEcode;
    }

    public void setGlobalEcode(int globalEcode) {
        this.globalEcode = globalEcode;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public ValidateChecker getChecker() {
        return checker;
    }

    public void setChecker(ValidateChecker checker) {
        this.checker = checker;
    }

    public boolean isFastFailed() {
        return fastFailed;
    }

    public void setFastFailed(boolean fastFailed) {
        this.fastFailed = fastFailed;
    }

    public boolean isValidateInside() {
        return validateInside;
    }

    public void setValidateInside(boolean validateInside) {
        this.validateInside = validateInside;
    }

    @Override
    public String toString() {
        return "ValidateContext{" + "globalValidateException=" + globalValidateException + ", globalEcode="
                        + globalEcode + ", groups=" + groups + ", checker=" + checker + ", fastFailed=" + fastFailed
                        + ", validateInside=" + validateInside + '}';
    }
}
