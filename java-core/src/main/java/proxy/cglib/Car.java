package proxy.cglib;

/**
 * @author: zucker
 * @description:
 * @date: 2019-07-12 18:09
 */
public class Car {
    public void run(){
        System.out.println("run---");
    }

    public void run(String zucker){
        System.out.println("zucker---run---");
    }

    private void stop(){
        System.out.println("stop---");
    }
}
