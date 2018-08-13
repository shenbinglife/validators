package io.github.shenbinglife.validators.validators;

import java.util.Collection;
import java.util.Map;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.Length;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class LengthValidator implements ComplexValidator<Object, Length> {
    @Override
    public boolean validate(Object object, Length annotation, ValidateContext context) {
        if (object == null) {
            return true;
        }

        int num;
        if (object instanceof String) {
            num = ((String) object).length();
        } else if (object.getClass().isArray()) {
            num = ((Object[]) object).length;
        } else if (object instanceof Collection) {
            num = ((Collection) object).size();
        } else if (object instanceof Map) {
            num = ((Map) object).keySet().size();
        } else {
            throw new IllegalArgumentException("不支持的检查长度的对象类型:" + object.getClass());
        }
        return (annotation.zeroAble() && num == 0) || (num >= annotation.min() && num <= annotation.max());
    }
}
