package proxy;

import common.RpcInvocation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @Author: Zucker
 * @Date: 2020/3/27 2:38 PM
 * @Description
 */
public class RpcInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setMethodName(method.getName());
        rpcInvocation.setArgCls(method.getParameterTypes());
        rpcInvocation.setInterfaceName(proxy.getClass().getInterfaces()[0].getName());
        rpcInvocation.setArgs(args);
        Object retObj = null;
        //TODO 从注册中心拉取服务列表，注册中心选型插件式



        //TODO 执行负债均衡逻辑，负债策略插件式

        //TODO 远程调用，具体实现插件式
        System.out.println("----- invocation:" + rpcInvocation);
        try  {
            Socket socket = new Socket("localhost", 9999);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            output.writeObject(rpcInvocation);
            output.flush();

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            retObj = input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retObj;
    }


}
