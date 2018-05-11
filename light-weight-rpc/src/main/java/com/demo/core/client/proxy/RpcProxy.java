package com.demo.core.client.proxy;

import com.demo.core.client.RpcClient;

/**
 * Created by dragon
 *
 * 生成代理类
 */
public interface RpcProxy {
    //客户端 生成调用的接口的代理类。
    <T> T proxyInterface(RpcClient rpcClient, final Class<T> serviceInterface);
}
