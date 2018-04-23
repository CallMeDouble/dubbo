package com.dragon.pattern.DynamicProxy;

/**
 * Created by dragon
 */
public class TestDynamicProxy {
    public static void main(String[] args) {
        JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy(new RealObject());
        ISubjectObject proxy = (ISubjectObject) jdkDynamicProxy.getProxy();
        proxy.test();
        proxy.test2();
    }
}
