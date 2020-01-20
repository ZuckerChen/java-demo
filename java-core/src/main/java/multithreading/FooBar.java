package multithreading;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 可重入锁+条件队列
 */
class FooBar {
    private int n;

    public FooBar(int n) {
        this.n = n;
    }
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private volatile boolean flag = false;

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();

            if(flag){
                condition.await();
            }
            flag = true;
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            condition.signal();
            lock.unlock();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            if(!flag){
                condition.await();
            }
            flag = false;
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            condition.signal();
            lock.unlock();
        }
    }

}