package com.demo.service;

import com.demo.core.server.RpcServer;
import com.demo.core.server.RpcServerBuilder;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PreDestroy;

/**
 * Created by dragon
 *
 * 进一步封装
 * 用工厂类封装，用于创建RpcServer对象, 并且开启服务器
 */
public class ServerFactoryBean implements FactoryBean<Object>{

    //当前服务的名字
    private String serviceName;
    private Object serviceImpl;

    private int port;
    private String zkConn;

    //rpcServer端
    private RpcServer rpcServer;

    /**
     * 开启服务端
     */
    public void start(){
        rpcServer = RpcServerBuilder.builder().setPort(port).setServiceImpl(serviceImpl).setServiceName(serviceName)
                .setZkConn(zkConn).build();
        rpcServer.start();
    }

    //服务下线
    @PreDestroy
    public void serviceOffline(){
        rpcServer.shutdown();
    }

    @Override
    public Object getObject() throws Exception {
        return rpcServer;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getZkConn() {
        return zkConn;
    }

    public void setZkConn(String zkConn) {
        this.zkConn = zkConn;
    }

    public RpcServer getRpcServer() {
        return rpcServer;
    }

    public void setRpcServer(RpcServer rpcServer) {
        this.rpcServer = rpcServer;
    }
}
