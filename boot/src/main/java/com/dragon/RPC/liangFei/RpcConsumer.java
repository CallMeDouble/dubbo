package com.dragon.RPC.liangFei;

/**
 * Created by dragon
 */
public class RpcConsumer {
    public static void main(String[] args) throws Exception {
        HelloService helloService = RpcFrameWork.refer(HelloService.class, "127.0.0.1", 8080);
        System.out.println(helloService.helloWorld("world"));;
    }
}
