package com.dragon.threadTest.InterruptTest;

/**
 * Created by zhushuanglong on 2018/2/24.
 */
public class InterrputDemo3 {
    public static void main(String[] args) {
        System.out.println("bb");
        Thread.currentThread().interrupt();
        System.out.println("aa");


    }
}
