package io.github.shenbinglife.validators;

import static io.github.shenbinglife.validators.ValidatorNames.*;

import io.github.shenbinglife.validators.validators.*;

/**
 * 简易的校验器注册中心，默认已经注册一些基础的校验器
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class SimpleValidatorRegistry extends ValidatorRegistry {


    public SimpleValidatorRegistry() {
        super();
        register(BLANK_VALIDATOR, new StringBlankValidator());
        register(EQUALS_VALIDATOR, new EqualsValidator());
        register(FALSE_VALIDATOR, new FalseValidator());
        register(IN_VALIDATOR, new InValidator());
        register(IN_ENUM_VALIDATOR, new InEnumValidator());
        register(INT_RANGE_VALIDATOR, new IntRangeValidator());
        register(LENGTH_VALIDATOR, new LengthValidator());
        register(MULTI_VALIDATE_VALIDATOR, new MultiValidator());
        register(NOT_BLANK_VALIDATOR, new StringNotBlankValidator());
        register(NOT_NULL_VALIDATOR, new NotNullValidator());
        register(NULL_VALIDATOR, new NullValidator());
        register(REGEX_VALIDATOR, new RegexValidator());
        register(TRUE_VALIDATOR, new TrueValidator());
        register(ALWAYS_TRUE, new AlwaysTrue());
        register(EACH_VALIDATOR, new EachValidator());
    }
}
