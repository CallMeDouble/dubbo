package com.dragon.pattern.Command;

/**
 * Created by zhushuanglong on 2018/3/21.
 */
public class WorkerOne extends Worker {
    @Override
    void doSomething() {
        System.out.println("WorkerOne doSomething");
    }

    @Override
    void doSomething2() {
        System.out.println("WorkerOne doSomething2");
    }

    @Override
    void doSomething3() {
        System.out.println("WorkerOne doSomething3");
    }
}
