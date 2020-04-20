package consumer;

import common.ServiceAddr;
import proxy.RpcInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.List;


/**
 * @Author: Zucker
 * @Date: 2020/3/26 5:24 PM
 * @Description
 */
public class ConsumerBootstrap {
    /**
     * 服务地址列表
     */
    private static List<ServiceAddr> serviceAddrList;

    public static void start() {
//        while (true) {
//            try (Socket socket = new Socket("localhost", 9999);
//                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                 PrintWriter output = new PrintWriter(socket.getOutputStream())) {
//
//                System.out.println("请输入:");
//                BufferedReader sendMsg = new BufferedReader(new InputStreamReader(System.in));
//                output.println(sendMsg.readLine());
//                output.flush();
//
//                String returnMsg = input.readLine();
//                System.out.println("服务端返回:" + returnMsg);
//                if ("OK".equalsIgnoreCase(returnMsg)) {
//                    System.out.println("客户端关闭连接");
//                    break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /** 返回代理对象 */
    public <T> T findService(Class<T> interfaceName){
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[]{interfaceName}, new RpcInvocationHandler());
    }

    void lookupProvider(String interfaceName){

    }
}
