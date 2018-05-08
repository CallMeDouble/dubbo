package com.demo.service;

import com.demo.annotation.RpcService;
import demo.dubbo.service.HelloService;

/**
 * Created by dragon
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String name) {
        return null;
    }
}
