package simple;

import java.io.Serializable;

/**
 * @Author: Zucker
 * @Date: 2020/3/27 11:36 AM
 * @Description
 */
public class TestVO implements Serializable {
    private static final long serialVersionUID = -1911032940294248260L;

    String name;
    Integer age;

    public String getName() {
        return name;
    }

    public TestVO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public TestVO setAge(Integer age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "TestVO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
