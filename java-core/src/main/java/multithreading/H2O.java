package multithreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class H2O {


    public H2O() {
    }

    CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
        @Override
        public void run() {
            semaphore_h.release(2);
            semaphore_o.release();
            cb.reset();
        }
    });

    Semaphore semaphore_h = new Semaphore(2);

    Semaphore semaphore_o = new Semaphore(1);


    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        semaphore_h.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        try {
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        semaphore_o.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        try {
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}