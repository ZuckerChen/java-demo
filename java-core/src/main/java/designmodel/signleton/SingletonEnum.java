package designmodel.signleton;


/**
 * @Author: Zucker
 * @Date: 2020/3/3 2:12 PM
 * @Description
 */
public class SingletonEnum {
    private SingletonEnum() {
    }

    public static SingletonEnum getInstance() {
        return InnerEnum.INSTANCE.getSingleton();
    }

    enum InnerEnum {
        INSTANCE;
        private SingletonEnum singleton;

        InnerEnum() {
            singleton = new SingletonEnum();
        }

        public SingletonEnum getSingleton() {
            return singleton;
        }

    }

}



