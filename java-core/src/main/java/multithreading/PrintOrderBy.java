package multithreading;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch
 */
public class PrintOrderBy {

    CountDownLatch countDownLatch_one = new CountDownLatch(1);
    CountDownLatch countDownLatch_two = new CountDownLatch(1);


    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        countDownLatch_one.countDown();

    }

    public void second(Runnable printSecond) throws InterruptedException {
        countDownLatch_one.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        countDownLatch_two.countDown();

    }

    public void third(Runnable printThird) throws InterruptedException {
        countDownLatch_two.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
