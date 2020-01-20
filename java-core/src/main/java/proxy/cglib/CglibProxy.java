package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: zucker
 * @description:
 * @date: 2019-07-12 18:10
 */
public class CglibProxy<T> implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public <T> T getProxy(Class clazz) {
        //生成指定类对象的子类,也就是重写类中的业务函数，在重写中加入intercept()函数而已。
        enhancer.setSuperclass(clazz);
        //这里是回调函数，enhancer中肯定有个MethodInterceptor属性。
        //回调函数是在setSuperclass中的那些重写的方法中调用---猜想
        enhancer.setCallback(this);
        //创建这个子类对象
        return (T)enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(method.getName() + "执行之前做一些准备工作");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println(method.getName() + "执行之后做一些准备的工作");
        return result;
    }
}
