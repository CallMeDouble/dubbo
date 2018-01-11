package com.doubledragon.RpcWithTcp.provider;

import com.doubledragon.RpcWithTcp.base.SayHelloService;

/**
 * Created by zhushuanglong on 2018/1/11.
 */
public class SayHelloServiceImpl implements SayHelloService {
    @Override
    public String sayHello(String arg) {
        if(arg.equals("hello")){
            return "hello";
        }
        return "bye";
    }
}
