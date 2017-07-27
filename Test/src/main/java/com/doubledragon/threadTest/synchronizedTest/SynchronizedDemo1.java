package com.doubledragon.threadTest.synchronizedTest;

/**
 * Created by zhushuanglong on 2017/7/25.
 */
public class SynchronizedDemo1 implements Runnable {

    @Override
    public void run() {
        synchronized (this){
            for(int i=0; i<5;i++){
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " loop "+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
        Thread t1 = new Thread(synchronizedDemo1);
        Thread t2 = new Thread(synchronizedDemo1);
        t1.start();
        t2.start();
    }
}
