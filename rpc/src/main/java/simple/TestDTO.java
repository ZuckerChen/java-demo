package simple;

import java.io.Serializable;

/**
 * @Author: Zucker
 * @Date: 2020/3/27 11:36 AM
 * @Description
 */
public class TestDTO implements Serializable {

    private static final long serialVersionUID = -74734964992379208L;

    String param1;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    @Override
    public String toString() {
        return "TestDTO{" +
                "param1='" + param1 + '\'' +
                '}';
    }
}
