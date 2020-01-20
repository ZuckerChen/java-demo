package lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Zucker
 * @Date: 2020-01-05 14:46
 * @Description 阻塞队列版生产者消费者模式
 *              涉及知识：volatile/CAS/atomicinteger/BlockQueue
 */
public class ProdConsumer_BlockingQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线成启动");
            try {
                myResource.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线成启动");
            try {
                myResource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("5秒结束，大老板叫停，活动结束");
        myResource.stop();
    }

}

class MyResource {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void stop() {
        FLAG = false;
    }

    public void prod() throws InterruptedException {
        String data = null;
        boolean retValue;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 大老板叫停 ，表示FLAG为FALSE，生产动作结束");
    }

    public void consumer() throws InterruptedException {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equals("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过两秒没有取到蛋糕，消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列蛋糕" + result + "成功");
        }
    }
}