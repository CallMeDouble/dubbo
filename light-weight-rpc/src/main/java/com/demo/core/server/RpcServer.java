package com.demo.core.server;

/**
 * Created by double
 *
 * rpc的服务端，开启服务即开启netty，接收客户端请求，并响应
 */
public interface RpcServer {

    void start();

    void shutdown();
}
