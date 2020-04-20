package provider;

import common.RpcInvocation;
import simple.TestImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Zucker
 * @Date: 2020/3/25 2:51 PM
 * @Description 1、启动一个端口监听
 * 2、获取可用服务地址
 * 3、
 */
public class ProviderBootstrap {
    /**
     * 服务列表
     */
    private static Map<String, Object> servicesMap = new ConcurrentHashMap();


    private static final Executor workPool = new ThreadPoolExecutor(
            10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(10));

    public static void start() {
        //注册服务
        servicesMap.put("simple.ITest", new TestImpl());
        try (ServerSocket ss = new ServerSocket(9999)) {
            while (true) {
                Socket client = ss.accept();
                workPool.execute(new WorkThread(client));
            }

        } catch (IOException e) {
            System.out.println("服务器异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class WorkThread implements Runnable {
        Socket socket;

        public WorkThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "等待处理。。。");

            try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                Object o = ois.readObject();
                if (o instanceof RpcInvocation) {
                    System.out.println(Thread.currentThread().getName() + " 开始处理。。。");
                    RpcInvocation rpcInvocation = (RpcInvocation) o;
                    Object targetService = servicesMap.get(rpcInvocation.getInterfaceName());
                    Method method = targetService.getClass().getMethod(rpcInvocation.getMethodName(), rpcInvocation.getArgCls());
                    Object result = method.invoke(targetService, rpcInvocation.getArgs());
                    oos.writeObject(result);
                    oos.flush();
                } else {
                    throw new ClassNotFoundException("对象类型不正确");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

