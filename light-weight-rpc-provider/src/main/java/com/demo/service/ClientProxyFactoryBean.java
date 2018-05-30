package com.demo.service;

import com.demo.core.client.RpcProxyBuilder;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by dragon
 *
 * 获取客户端的代理类
 */
public class ClientProxyFactoryBean<T> implements FactoryBean<T> {

    private String serviceName;
    private Class<T> serviceInterface;

    private String zkConn;

    @Override
    public T getObject() throws Exception {
        return RpcProxyBuilder.<T>builder()
                .setServiceName(serviceName)
                .setServiceinterface(serviceInterface)
                .setZkConn(zkConn)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
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

    public Class<T> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getZkConn() {
        return zkConn;
    }

    public void setZkConn(String zkConn) {
        this.zkConn = zkConn;
    }
}
