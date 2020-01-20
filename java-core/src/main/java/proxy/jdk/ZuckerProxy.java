package proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: zucker
 * @description:
 * @date: 2019-07-12 17:34
 */
public class ZuckerProxy implements InvocationHandler {
    private Zucker zucker;

    public ZuckerProxy(Zucker zucker) {
        super();
        this.zucker = zucker;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("befor---");
        Object invoke = method.invoke(zucker, args);
        System.out.println("after---");
        return null;
    }
}
