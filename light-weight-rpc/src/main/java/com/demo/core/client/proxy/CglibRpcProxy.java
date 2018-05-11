package com.demo.core.client.proxy;

import com.demo.core.client.RpcClient;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by dragon
 *
 * 利用cglib实现rpc代理
 */
public class CglibRpcProxy implements RpcProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(CglibRpcProxy.class);

    //利用cglib生成代理类
    @Override
    public <T> T proxyInterface(RpcClient rpcClient, Class<T> serviceInterface) {
        LOGGER.info("生成代理类");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceInterface);
        enhancer.setCallback(new cglibInteceptor(rpcClient, serviceInterface));
        Object enhancerObject = enhancer.create();
        return (T)enhancerObject;
    }

    //使用一个静态内部类来做 method的cglib代理
    private static class cglibInteceptor implements MethodInterceptor {

        //获取基本方法
        private static Method hashCodeMethod;
        private static Method equalsMethod;
        private static Method toStringMethod;

        static{
            try {
                hashCodeMethod = Object.class.getMethod("hashCode");
                equalsMethod = Object.class.getMethod("equals", Object.class);
                toStringMethod = Object.class.getMethod("toString");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        //加入客户端和所调用的服务接口
        private RpcClient rpcClient;
        private Class<?> serviceInterface;

        public cglibInteceptor(RpcClient rpcClient, Class<?> serviceInterface) {
            this.rpcClient = rpcClient;
            this.serviceInterface = serviceInterface;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy)
                throws Throwable {
            //先对方法进行判断是否是通用方法，假如都不是，最后再通过client来调用
            if (hashCodeMethod.equals(method)) {
                return proxyHashCode(proxy);
            }
            if (equalsMethod.equals(method)) {
                return proxyEquals(proxy, objects[0]);
            }
            if (toStringMethod.equals(method)) {
                return proxyToString(proxy);
            }

            //让客户端发送调用信息（利用netty），并拿到返回值
            LOGGER.info("客户端发送调用信息");
            return rpcClient.sendMessage(serviceInterface, method, objects).getResponse();
        }

        /**
         * 针对基础方法做相应的策略
         * @param proxy
         * @return
         */
        private int proxyHashCode(Object proxy) {
            return System.identityHashCode(proxy);
        }
        private boolean proxyEquals(Object proxy, Object other) {
            return (proxy == other);
        }
        private String proxyToString(Object proxy) {
            return proxy.getClass().getName() + '@' + Integer.toHexString(proxy.hashCode());
        }

    }
}
