package common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: Zucker
 * @Date: 2020/3/27 11:28 AM
 * @Description
 */
public class RpcInvocation implements Serializable {
    private static final long serialVersionUID = -2339680749539338242L;

    private String interfaceName;

    private String methodName;

    private Class[] argCls;

    private Object[] args;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getArgCls() {
        return argCls;
    }

    public void setArgCls(Class[] argCls) {
        this.argCls = argCls;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "RpcInvocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", argCls=" + Arrays.toString(argCls) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
