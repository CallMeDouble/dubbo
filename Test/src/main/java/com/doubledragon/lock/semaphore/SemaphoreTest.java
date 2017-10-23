package com.doubledragon.lock.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by zhushuanglong on 2017/10/18.
 */
public class SemaphoreTest {
    private static Semaphore semaphore;

    public static void main(String[] args) {
        semaphore = new Semaphore(10);

        TestThread testThread = new TestThread(semaphore, 4);
        new Thread(testThread).start();
        TestThread testThread2 = new TestThread(semaphore, 5);
        new Thread(testThread2).start();
        TestThread testThread3 = new TestThread(semaphore, 7);
        new Thread(testThread3).start();
    }
}

class TestThread implements Runnable{
    private Semaphore semaphore;
    private int count;

    public TestThread(Semaphore semaphore,int count) {
        this.semaphore = semaphore;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "准备获取信号量");
        try {
            semaphore.acquire(count);
            System.out.println(Thread.currentThread().getName() +"获取了"+count+"个信号量");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release(count);
        }
    }
}
