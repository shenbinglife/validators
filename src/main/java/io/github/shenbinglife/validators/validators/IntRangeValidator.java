package io.github.shenbinglife.validators.validators;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import io.github.shenbinglife.validators.ComplexValidator;
import io.github.shenbinglife.validators.ValidateContext;
import io.github.shenbinglife.validators.anno.cfg.IntRange;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class IntRangeValidator implements ComplexValidator<Object, IntRange> {

    private static Set<Class<?>> NumberTypes = new HashSet<>();
    static {
        NumberTypes.add(Integer.class);
        NumberTypes.add(Long.class);
        NumberTypes.add(Double.class);
        NumberTypes.add(Float.class);
        NumberTypes.add(int.class);
        NumberTypes.add(long.class);
        NumberTypes.add(double.class);
        NumberTypes.add(float.class);
        NumberTypes.add(BigDecimal.class);
        NumberTypes.add(BigInteger.class);
    }

    @Override
    public boolean validate(Object object, IntRange annotation, ValidateContext context) {
        if (object == null) {
            return true;
        }
        BigDecimal num;
        if (object instanceof String) {
            num = NumberUtils.createBigDecimal((String) object);
        } else if (NumberTypes.contains(object.getClass())) {
            String numString = String.valueOf(object);
            num = NumberUtils.createBigDecimal(numString);
        } else {
            throw new IllegalArgumentException("不支持的数字格式:" + object.toString());
        }
        BigDecimal max = new BigDecimal(annotation.max());
        BigDecimal min = new BigDecimal(annotation.min());

        return max.compareTo(num) >= 0 && min.compareTo(num) <= 0;
    }
}
