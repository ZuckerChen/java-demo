package multithreading;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    Semaphore semaphore_z = new Semaphore(1);
    Semaphore semaphore_e = new Semaphore(0);
    Semaphore semaphore_o = new Semaphore(0);


    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            semaphore_z.acquire();
            printNumber.accept(0);
            if (i % 2 == 0) {
                semaphore_o.release();
            } else {
                semaphore_e.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <=n; i += 2) {
            semaphore_e.acquire();
            printNumber.accept(i);
            semaphore_z.release();
        }

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <=n; i+=2) {
            semaphore_o.acquire();
            printNumber.accept(i);
            semaphore_z.release();
        }
    }

}