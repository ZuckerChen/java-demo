package designmodel.signleton;

/**
 * @Author: Zucker
 * @Date: 2020/3/3 2:03 PM
 * @Description
 * 双重锁模式
 * 线程安全，延迟初始化。这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
 */
public class SingletonDoubleCheckLock {
    private SingletonDoubleCheckLock(){}

    private static volatile SingletonDoubleCheckLock instance;

    private static Object obj = new Object();

    public static SingletonDoubleCheckLock getInstance(){
        if(null == instance){
            synchronized (obj){
                if(null == instance){
                    instance = new SingletonDoubleCheckLock();
                }
            }
        }
        return instance;
    }
}
