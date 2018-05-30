package com.dragon.lock.MyReentrantLock;

/**
 * Created by zhushuanglong on 2017/10/27.
 */
public class Test {
    private static TwinsLock tl = new   TwinsLock();

    public static void main(String[] args) {
        for(int i = 0 ; i<5 ;i ++){
            new Thread(new MyThread()).start();
        }

        for(int i =0; i <10; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyThread implements  Runnable{

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + "准备获取锁");
            tl.lock();
            try {
                Thread.sleep(1000);
                System.out.println(name + "获取到了锁");
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "睡眠完毕，准备释放锁");
            tl.release();
        }
    }

}
