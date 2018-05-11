package com.demo.core.client;

import com.demo.core.server.Response;

import java.lang.reflect.Method;

/**
 * Created by dragon
 *
 * 客户端client的实现，
 * 实现客户端以及代理类的生成
 * 可以利用动态代理，去调用真实的
 */
public interface RpcClient {
    //获取代理接口, 用增强加强调用的接口！！！使其能够调用本类的sendMessage方法，该方法用于调用远程方法
    <T> T proxyInterface(Class<T> serviceInterface) throws IllegalAccessException, InstantiationException;

    //增强调用的接口，这里去调用远程方法
    Response sendMessage(Class<?> serviceInterface, Method method, Object[] args);

    //关闭客户端连接，结束请求
    void close();
}
