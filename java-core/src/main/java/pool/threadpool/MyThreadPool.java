package pool.threadpool;

/**
 * @Author: Zucker
 * @Date: 2020/3/13 11:26 AM
 * @Description
 */
public interface MyThreadPool {

    /**
     * 添加任务，执行
     *
     * @param myTask
     */
    void execute(MyTask myTask);

    /**
     * 等任务执行完，再关闭
     */
    void shutdown();

    /**
     * 添加工作线程
     */
    void addWorker();

    /**
     * 删除工作线程
     */
    void removeWorker();
}
