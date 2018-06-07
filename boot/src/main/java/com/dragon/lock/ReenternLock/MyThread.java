package com.dragon.lock.ReenternLock;

/**
 * Created by zhushuanglong on 2017/10/12.
 */
public class MyThread extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            super.run();
            System.out.println("i="+(i+1));
        }
    }

}