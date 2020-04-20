package pool.threadpool;

/**
 * @Author: Zucker
 * @Date: 2020/3/12 5:16 PM
 * @Description
 */
public class MyTask {
    private int taskId;

    public MyTask(int taskId) {
        this.taskId = taskId;
    }

    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "task :" + taskId + " 处理中...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getName() + "task :" + taskId + " 处理完毕...");
    }

    public int getTaskId() {
        return taskId;
    }
}
