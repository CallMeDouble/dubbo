package com.dragon.RPC.liangFei.impl;

import com.dragon.RPC.liangFei.HelloService;

/**
 * Created by dragon
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String helloWorld(String name) {
        return "hello " + name;
    }
}
