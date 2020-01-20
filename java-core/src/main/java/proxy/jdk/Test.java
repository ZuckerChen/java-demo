package proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author: zucker
 * @description:
 * @date: 2019-07-12 17:38
 */
public class Test {
    public static void main(String[] args) {
        Person person =(Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, new ZuckerProxy(new Zucker()));
        person.eat();
        person.sleep();
    }
}
