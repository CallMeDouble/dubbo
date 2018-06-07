package com.doubledragon.threadTest.InterruptTest;

/**
 * Created by zhushuanglong on 2017/10/25.
 */
public class InterrputDemo2 {

    public static void main(String[] args) {

        Thread thread = new Thread(new MyThread());
        thread.start();
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }

    static class MyThread implements Runnable{

        @Override
        public void run(){
            synchronized (this){
                try {
                    wait();
                    System.out.println("aa");
                } catch (InterruptedException e) {


                }
                System.out.println("bb");
            }
        }
    }


}
