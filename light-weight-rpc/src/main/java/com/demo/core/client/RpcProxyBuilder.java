package com.demo.core.client;

import com.demo.core.client.proxy.CglibRpcProxy;
import com.demo.core.client.proxy.RpcProxy;
import com.google.common.base.Preconditions;

/**
 * Created by dragon
 *
 * 创建远程调用代理类！！！！
 */
public class RpcProxyBuilder<T> {
    private String zkConn;
    private String serviceName;
    private Class<T> serviceinterface;
    private int requestTimeOutMillis = 10 * 1000;
    private Class<? extends RpcProxy> rpcProxyClass = CglibRpcProxy.class;

    public RpcProxyBuilder() {
    }

    public static <T> RpcProxyBuilder<T> builder(){
        return new RpcProxyBuilder<T>();
    }

    public T build() throws Exception {
        Preconditions.checkNotNull(serviceName);
        Preconditions.checkNotNull(zkConn);
        Preconditions.checkNotNull(rpcProxyClass);
        Preconditions.checkNotNull(requestTimeOutMillis);
        Preconditions.checkNotNull(serviceinterface);

        RpcClientImpl rpcClient = new RpcClientImpl(serviceName, zkConn, rpcProxyClass, requestTimeOutMillis);
        return rpcClient.proxyInterface(serviceinterface);
    }

    public String getZkConn() {
        return zkConn;
    }

    public RpcProxyBuilder<T> setZkConn(String zkConn) {
        this.zkConn = zkConn;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public RpcProxyBuilder<T> setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public Class<T> getServiceinterface() {
        return serviceinterface;
    }

    public RpcProxyBuilder<T> setServiceinterface(Class<T> serviceinterface) {
        this.serviceinterface = serviceinterface;
        return this;
    }

    public int getRequestTimeOutMillis() {
        return requestTimeOutMillis;
    }

    public RpcProxyBuilder<T> setRequestTimeOutMillis(int requestTimeOutMillis) {
        this.requestTimeOutMillis = requestTimeOutMillis;
        return this;
    }

    public Class<? extends RpcProxy> getRpcProxyClass() {
        return rpcProxyClass;
    }

    public RpcProxyBuilder<T> setRpcProxyClass(Class<? extends RpcProxy> rpcProxyClass) {
        this.rpcProxyClass = rpcProxyClass;
        return this;
    }
}
