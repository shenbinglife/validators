package io.github.shenbinglife.validators;

/**
 * 校验结果
 *
 * @author shenbing
 * @version 2017/11/27
 * @since since
 */
public class SimpleValidateResult implements ValidateResult {

    private ValidatedTarget target;

    private ValidatorProperty property;

    private boolean pass;

    public SimpleValidateResult(ValidatedTarget target, ValidatorProperty property, boolean pass) {
        this.target = target;
        this.property = property;
        this.pass = pass;
    }

    @Override
    public ValidatedTarget getTarget() {
        return target;
    }

    public void setTarget(ValidatedTarget target) {
        this.target = target;
    }

    public ValidatorProperty getProperty() {
        return property;
    }

    public void setProperty(ValidatorProperty property) {
        this.property = property;
    }

    @Override
    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "SimpleValidateResult{" + "target=" + target.getTarget() + ", validator=" + property + ", pass=" + pass
                        + '}';
    }
}
