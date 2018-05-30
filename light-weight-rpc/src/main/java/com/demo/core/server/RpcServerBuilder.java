package com.demo.core.server;

import com.google.common.base.Preconditions;

/**
 * Created by dragon
 *
 * 用于创建RpcServer对象
 */
public class RpcServerBuilder {

    private int port;
    private String serviceName;
    private Object serviceImpl;
    private String zkConn;

    public RpcServerBuilder() {
    }

    public static RpcServerBuilder builder(){
        return new RpcServerBuilder();
    }

    public RpcServer build(){
        Preconditions.checkNotNull(serviceName);
        Preconditions.checkNotNull(serviceImpl);
        Preconditions.checkNotNull(zkConn);
        Preconditions.checkArgument(port > 0 && port < 65535);
        return new RpcServerImpl(port, serviceImpl, serviceName, zkConn);
    }

    public int getPort() {
        return port;
    }

    public RpcServerBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public RpcServerBuilder setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public RpcServerBuilder setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }

    public String getZkConn() {
        return zkConn;
    }

    public RpcServerBuilder setZkConn(String zkConn) {
        this.zkConn = zkConn;
        return this;
    }
}
