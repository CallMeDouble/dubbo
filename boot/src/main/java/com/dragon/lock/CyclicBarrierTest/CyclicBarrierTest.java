package com.dragon.lock.CyclicBarrierTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by zhushuanglong on 2017/10/26.
 */
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        for(int i =0 ;i<5;i++){
            Thread thread = new Thread(myThread);
            thread.start();
            Thread.sleep(1000);
        }
    }

    static class MyThread implements Runnable{

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"a");
        }
    }
}
