package proxy.cglib;

/**
 * @author: zucker
 * @description:
 * @date: 2019-07-12 18:12
 */
public class Test {
    public static void main(String[] args) {
        CglibProxy<Car> cglibProxy = new CglibProxy();
        Car car = cglibProxy.getProxy(Car.class);
        car.run();
        car.run("zucker");
    }
}
