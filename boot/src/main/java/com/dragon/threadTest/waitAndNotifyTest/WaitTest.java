package com.dragon.threadTest.waitAndNotifyTest;

/**
 * Created by zhushuanglong on 2017/7/26.
 */
public class WaitTest {

    public static void main(String[] args) {
        ThreadA a = new ThreadA();

        Thread t1 = new Thread(a, "t1");
        Thread t2 = new Thread(a, "t2");

        synchronized (a){
            //q
            System.out.println(Thread.currentThread().getName() + " 启动线程t1");
            t1.start();

            System.out.println(Thread.currentThread().getName() + " 启动线程t2");
            t2.start();

            //主线程等待t1通过notify()唤醒
            System.out.println(Thread.currentThread().getName() + "  wait()");
            try {
                a.wait();

                System.out.println(Thread.currentThread().getName() + " continue");
            } catch (InterruptedException e) {


            }

        }
    }
}

class ThreadA implements Runnable{

    @Override
    public void run(){
        synchronized (this){
            System.out.println(Thread.currentThread().getName() + "call notify()");
            //唤醒当前正在wait的线程
            notify();
        }
    }
}
