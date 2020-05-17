package exception;

/**
 * @author zucker
 * @description
 * @date: 2020/4/16 4:08 PM
 */
public class ThreadException {
    public static void main(String[] args) {
        System.out.println("thread_main:" + Thread.currentThread().getId());

        Thread thread = new Thread(() -> {
            System.out.println("thread1:" + Thread.currentThread().getId());
            throw new NullPointerException();
        });

        thread.setUncaughtExceptionHandler(new MyExceptionHandle());
        thread.start();
    }


    static class MyExceptionHandle implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {

            System.out.println("thread handle:" + Thread.currentThread().getId());
            e.printStackTrace();

            System.out.println(e.toString());
            for (StackTraceElement se : e.getStackTrace()) {
                System.out.println("主线程获取:" + se.toString());
            }
        }
    }
}
