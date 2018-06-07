package com.dragon.CallBack;


/**
 * Created by dragon
 */
public class Process {

    public void process(CallBack callBack, String question){
        System.out.println("接收到question：" +question );
        System.out.println("思考一下");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("调用回调接口：callBack.callBack，将答案发回去");
        callBack.callBack("这个问题的答案是：2");
    }
}
