package com.dragon.RPC.RpcWithTcp.consumer;

import com.doubledragon.RPC.RpcWithTcp.base.SayHelloService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //①先去的接口的名称、需要调用的方法和需要传递的参数
        String interfaceName = SayHelloService.class.getName();
        Method method = SayHelloService.class.getMethod("sayHello", String.class);
        Object[] arguments={"hell1o"};
        Socket socket = new Socket("127.0.0.1", 1234);

        //②通过socket将上述内容发送到服务提供方
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        output.writeUTF(interfaceName);
        output.writeUTF(method.getName());
        output.writeObject(method.getParameterTypes());
        output.writeObject(arguments);

        //③等待服务器端相应结果。
        //此处使用阻塞式I/O，实际生产环境中处于性能的考虑，往往使用非阻塞I/O
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());//在获取到结果之前会被阻塞
        Object result = input.readObject();
        System.out.println("result:" + result);

    }
}
