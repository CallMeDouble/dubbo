package com.doubledragon.threadTest.synchronizedTest;

/**
 * Created by zhushuanglong on 2017/7/25.
 */
class Count {

    // 含有synchronized同步块的方法
    public void synMethod() {
        synchronized (this) {
            System.out.println("count 的同步方法的this对象：" + this);
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                }
            } catch (InterruptedException ie) {
            }
        }
    }

    // 非同步的方法
    public void nonSynMethod() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " nonSynMethod loop " + i);
            }
        } catch (InterruptedException ie) {
        }
    }
}

public class SynchronizedDemo2 {

    public static void main(String[] args) {
        final Count count = new Count();
        // 新建t1, t1会调用“count对象”的synMethod()方法

        class mythread1 implements Runnable {
            @Override
            public void run() {
                System.out.println("t1 的run方法的this对象：" + this);
                count.synMethod();
            }
        }
        Thread t1 = new Thread(new mythread1(), "t1");

        class mythread2 implements Runnable {

            @Override
            public void run() {
                System.out.println("t2 的run方法的this对象：" + this);
                count.synMethod();
            }
        }

        // 新建t2, t2会调用“count对象”的nonSynMethod()方法
        Thread t2 = new Thread(new mythread2(), "t2");

        t1.start(); // 启动t1
        t2.start(); // 启动t2
    }
}