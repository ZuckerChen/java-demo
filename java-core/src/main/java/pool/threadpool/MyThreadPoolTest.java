package pool.threadpool;

/**
 * @Author: Zucker
 * @Date: 2020/3/13 5:14 PM
 * @Description
 */
public class MyThreadPoolTest {
    public static void main(String[] args) {

        MyThreadPool threadPool = new MyThreadPoolExecutor(5, 10, 15);

        for (int i = 0; i < 40; i++) {
            MyTask myTask = new MyTask(i);
            threadPool.execute(myTask);
        }

        threadPool.shutdown();

    }
}
