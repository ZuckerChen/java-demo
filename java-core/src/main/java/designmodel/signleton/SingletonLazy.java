package designmodel.signleton;

/**
 * @Author: Zucker
 * @Date: 2020/3/3 1:51 PM
 * @Description
 * 懒汉模式
 * 线程不安全，延迟初始化，严格意义上不是不是单例模式
 */
public class SingletonLazy {
    private SingletonLazy() {
    }

    private static SingletonLazy instance;

    public static SingletonLazy getInstance() {
        if (null == instance) {
            instance = new SingletonLazy();
        }
        return instance;
    }
}
