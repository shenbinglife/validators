package io.github.shenbinglife.validators;

import io.github.shenbinglife.validators.anno.cfg.IntRange;
import io.github.shenbinglife.validators.anno.cfg.Length;
import io.github.shenbinglife.validators.anno.cfg.NotBlank;
import io.github.shenbinglife.validators.anno.meta.ValidateInside;

import java.util.List;


/**
 * CLASS_NAME
 *
 * @author shenbing
 * @version 2017/11/21
 * @since since
 */
@ValidateInside
public class User {

    @IntRange(max = 1880, min = 5)
    private int age;

    @NotBlank
    private String name;

    @Length(min = 10)
    private List<String> list;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
