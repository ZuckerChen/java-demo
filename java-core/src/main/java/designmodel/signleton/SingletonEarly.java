package designmodel.signleton;

/**
 * @Author: Zucker
 * @Date: 2020/3/3 1:55 PM
 * @Description
 * 饿汉模式
 * 线程安全，比较常用，但容易产生垃圾，因为一开始就初始化
 */
public class SingletonEarly {
    private SingletonEarly() {
    }
    private static SingletonEarly instance = new SingletonEarly();

    public static SingletonEarly getInstance(){
        return instance;
    }
}
