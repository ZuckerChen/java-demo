package designmodel.signleton;

/**
 * @Author: Zucker
 * @Date: 2020/3/3 2:08 PM
 * @Description
 * 静态内部类单例模式
 * 只有第一次调用getInstance方法时，虚拟机才加载 Holder 并初始化instance
 */
public class SingletonInnerClass {
    private SingletonInnerClass(){}

    private static class Holder{
        private static final SingletonInnerClass instance = new SingletonInnerClass();
    }

    public static SingletonInnerClass getInstance(){
        return Holder.instance;
    }
}
