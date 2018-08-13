package io.github.shenbinglife.validators;

import com.google.common.collect.Lists;
import io.github.shenbinglife.validators.anno.cfg.Blank;
import io.github.shenbinglife.validators.anno.meta.MsgFiller;

import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/17
 * @since since
 */
public class Test {

    @org.junit.Test
    public void testValidator() {
        User user = new User();
        user.setAge(0);
        user.setList(Lists.newArrayList("12"));
        user.setName("asdf");

        ValidateContext context = ValidateContext.newInstance();
        context.setFastFailed(false);

        ValidatedTarget validatedTarget = new ValidatedTarget(user, context);
        ValidateCollector check = validatedTarget.check();
        System.out.println(check.getSimpleValidateResults());

    }

    @org.junit.Test
    public void testMessageFormat() {
        String format = MessageFormat.format("{0}{4}{2}{3}", "1", "asdfasdf", "asdf", "", 456, "asdf");
        System.out.println(format);
    }

    @Blank
    @org.junit.Test
    public void getMessageSourceAnno() throws NoSuchMethodException {
        Blank blank = Test.class.getMethod("getMessageSourceAnno").getAnnotation(Blank.class);
        Method[] declaredMethods = blank.annotationType().getDeclaredMethods();
        for(Method m : declaredMethods) {
            MsgFiller annotation = m.getAnnotation(MsgFiller.class);
            if(annotation != null) {
                System.out.println(m.getName() + ":" + annotation.value());
            }
        }
    }
}
