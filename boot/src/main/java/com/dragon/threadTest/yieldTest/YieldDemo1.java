package com.dragon.threadTest.yieldTest;

/**
 * Created by zhushuanglong on 2017/7/26.
 */
// YieldTest.java的源码
class ThreadA extends Thread{
    public ThreadA(String name){
        super(name);
    }
    public synchronized void run(){
        for(int i=0; i <10; i++){
            System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
            // i整除4时，调用yield
            if (i%4 == 0) {
                //System.out.println(this.getName() + " before yield:" +i);
                Thread.yield();
                //System.out.println(this.getName() + " after yield:" +i);
            }
        }
    }
}

public class YieldDemo1{
    public static void main(String[] args){
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }
}