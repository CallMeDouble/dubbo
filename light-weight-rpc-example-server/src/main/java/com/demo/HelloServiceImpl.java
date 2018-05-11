package com.demo;

import demo.dubbo.service.HelloService;

/**
 * Created by dragon
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String name) {
        return "hello, "+ name;
    }
}
