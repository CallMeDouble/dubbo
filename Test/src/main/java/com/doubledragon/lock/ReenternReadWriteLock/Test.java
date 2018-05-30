package com.doubledragon.lock.ReenternReadWriteLock;

/**
 * Created by zhushuanglong on 2017/10/16.
 */
public class Test {
    public static void main(String[] args) {
        Count count = new Count(123456, 100);
        User zsl = new User("zsl", count);

        zsl.getCash();
        zsl.getCash();
        zsl.getCash();
//        zsl.getCash();
//        zsl.getCash();
//        zsl.getCash();
//        zsl.getCash();
//        zsl.getCash();
//        zsl.getCash();

//
//        zsl.getCash();
//        zsl.addCash(100);
//        zsl.getCash();
    }
}
