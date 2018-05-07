package com.dragon.RPC.liangFei;

import com.dragon.RPC.liangFei.impl.HelloServiceImpl;

/**
 * Created by dragon
 */
public class RpcPrivoder {
    public static void main(String[] args) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        RpcFrameWork.export(helloService, 8080);
    }
}
