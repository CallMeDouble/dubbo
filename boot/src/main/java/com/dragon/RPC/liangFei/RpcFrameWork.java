package com.dragon.RPC.liangFei;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dragon
 */
public class RpcDemo {

    /**
     * 暴露某个服务
     * 当服务被调用时，获取被暴露服务的某个方法，并调用该方法，并返回结果
     * @param service 服务接口
     * @param port 端口号
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void export(final Object service, int port) throws IOException {
        if (service == null) {
            throw new IllegalArgumentException("service instance == null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("invalid port:" + port);
        }
        System.out.println("export service +" + service.getClass().getName() + "on port:" + port);

        ServerSocket serverSocket = new ServerSocket(port);
        for(;;){
            Socket accept = serverSocket.accept();
            //开启线程接收输入
            new Thread(() -> {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                    //获取方法名
                    String methodName = objectInputStream.readUTF();
                    //获取参数类型
                    Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
                    //获取参数
                    Object[] arguments = (Object[]) objectInputStream.readObject();

                    //获取方法
                    Method method = service.getClass().getMethod(methodName, parameterTypes);
                    //调用方法，获取方法调用结果
                    Object result = method.invoke(service, arguments);

                    //输出结果
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
                    objectOutputStream.writeObject(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 调用某个服务
     * @param interfaceClass 要调用的服务的接口
     * @param host 主机号
     * @param port 端口号
     * @param <T> 接口泛型
     * @return 返回值
     */
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port){
        if(interfaceClass == null){
            throw new IllegalArgumentException("interface class == null");
        }
        if(!interfaceClass.isInterface()){
            throw new IllegalArgumentException("the "+ interfaceClass.getName() +"must be interface class");
        }
        if(host == null || host.length() == 0){
            throw new IllegalArgumentException("host == null");
        }
        if(port <=0 || port > 65535){
            throw new IllegalArgumentException("invalid port");
        }

        System.out.println("get remote service" + interfaceClass.getName() + "from server " + host + ":" + port);

        //返回代理对象，当调用方法时，会利用代理对象去调用远程对象方法!!!
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket(host, port);
                        //发送调用的方法信息和参数
                        try {
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            try {
                                objectOutputStream.writeUTF(method.getName());
                                objectOutputStream.writeObject(method.getParameterTypes());
                                objectOutputStream.writeObject(args);

                                //获取调用结果
                                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                                try {
                                    Object result = objectInputStream.readObject();
                                    if(result instanceof Throwable){
                                        throw (Throwable) result;
                                    }
                                    return result;
                                } finally {
                                    objectInputStream.close();
                                }
                            } finally {
                                objectOutputStream.close();
                            }
                        }finally {
                            socket.close();
                        }
                    }
        });
    }
}
