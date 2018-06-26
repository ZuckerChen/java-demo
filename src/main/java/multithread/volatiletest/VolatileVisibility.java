package multithread.volatiletest;

/**
 * Description:
 *
 * @version 1.0 2018/6/13 上午10:04 by zucker
 */
public class VolatileVisibility {
    int a = 0;
    volatile boolean flag = false;

    public void writer() {
        a = 1;                   //1
        flag = true;               //2
    }

    public void reader() {
        if (flag) {                //3
            int i = a;           //4
        }
    }

    @Override
    public String toString() {
        return "VolatileVisibility{" +
                "a=" + a +
                ", flag=" + flag +
                '}';
    }

    public static void main(String[] args) {
        VolatileVisibility visibility = new VolatileVisibility();
        Thread thread1 = new Thread(() -> {
            visibility.writer();
            System.out.println(visibility.toString());
        });


        Thread thread2 = new Thread(() -> {
            visibility.reader();
            System.out.println(visibility.toString());
        });

        thread2.start();
        thread1.start();

    }

//        static boolean flag = true;
//
//        public static void main (String[]args){
//            Thread thread1 = new Thread(() -> {
//                flag = false;
//            });
//
//            Thread thread2 = new Thread(() -> {
//                while (flag) {
//                    System.out.println("yes");
//                }
//            });
//            thread2.start();
//            thread1.start();
//
//    }
}
