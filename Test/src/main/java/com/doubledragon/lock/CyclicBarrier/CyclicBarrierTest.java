package com.doubledragon.lock.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by zhushuanglong on 2017/10/18.
 */
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier;

    public static void main(String[] args) {
        cyclicBarrier = new CyclicBarrier(3, new Thread(()->{
            System.out.println("执行最后一个要执行的线程");
        }));


        for(int i = 0;i <3;i++){
            new Thread(()->{
                System.out.println("wait");
                try {
                    cyclicBarrier.await();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "??");
            }).start();
        }
    }
}
