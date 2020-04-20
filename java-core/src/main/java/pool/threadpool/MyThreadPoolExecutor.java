package pool.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Zucker
 * @Date: 2020/3/12 5:15 PM
 * @Description
 */
public class MyThreadPoolExecutor implements MyThreadPool {
    private volatile int coreThreadSize;

    private BlockingQueue<MyTask> tasks;

    private BlockingQueue<MyWorker> workers;

    private volatile boolean shutdown = false;

    MyThreadPoolExecutor(int coreThreadSize, int maxThreadSize, int queueCapacity) {
        this.coreThreadSize = coreThreadSize;
        this.tasks = new LinkedBlockingQueue<>(queueCapacity);
        this.workers = new LinkedBlockingQueue<>(maxThreadSize);

        for (int i = 0; i < this.coreThreadSize; i++) {
            addWorker();
        }
    }

    @Override
    public void execute(MyTask myTask) {
        if (shutdown) {
            return;
        }

        if (null != myTask) {
            try {
                tasks.put(myTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void shutdown() {
        if (!shutdown) {
            shutdown = true;
            while (true) {
                if (tasks.size() == 0) {
                    workers.forEach(myWorker -> myWorker.stopRunning());
                    break;
                }
            }
            workers.clear();
        }

    }

    @Override
    public void addWorker() {
        MyWorker myWorker = new MyWorker();
        try {
            workers.put(myWorker);
            myWorker.start();
            System.out.println("创建第" + workers.size() + 1 + "个工作线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeWorker() {
        try {
            workers.take().stopRunning();
            System.out.println("销毁第" + workers.size() + "个工作线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class MyWorker extends Thread {
        private volatile boolean isRunning = true;

        @Override
        public void run() {
            //通过自选从队列中获取任务
            MyTask task = null;
            while (isRunning) {
                try {
                    task = tasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (null != task) {
                    System.out.println("thread name:" + this.getName() + " task id:" + task.getTaskId() + " 开始执行-->");
                    task.run();
                    task = null;
                }
            }
            System.out.println("thread name:" + this.getName() + " 销毁<--");
        }

        public void stopRunning() {
            this.isRunning = false;
        }
    }

}
