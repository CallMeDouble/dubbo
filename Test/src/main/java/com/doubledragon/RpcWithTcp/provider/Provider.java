package com.doubledragon.RpcWithTcp.provider;

import com.doubledragon.RpcWithTcp.base.SayHelloService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class Provider {
    private static Map<Class, Class> allServices = new HashMap<>();
    static{
        //相当于注册中心
        allServices.put(SayHelloService.class, SayHelloServiceImpl.class);
    }
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(1234);
        while(true){
            System.out.println("服务开始");
            Socket socket = serverSocket.accept();

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());//在获取到输入之前，会被阻塞
            System.out.println("请求进来了");
            //获取请求的内容
            String interfaceName = input.readUTF();
            String methodName = input.readUTF();
            Class[] parameterTypes = (Class[]) input.readObject();
            Object[] arguments = (Object[]) input.readObject();

            //接口类对象
            Class<?> serviceInterfaceClass = Class.forName(interfaceName);
            Method method = serviceInterfaceClass.getMethod(methodName, parameterTypes);
            //获取实现类对象
            Object service = allServices.get(serviceInterfaceClass).newInstance();
            Object result = method.invoke(service, arguments);

            //将结果返回给调用端
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(result);
        }
    }
}
