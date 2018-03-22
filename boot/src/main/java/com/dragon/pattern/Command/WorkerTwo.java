package com.dragon.pattern.Command;

/**
 * Created by zhushuanglong on 2018/3/21.
 */
public class WorkerTwo extends Worker {
    @Override
    void doSomething() {
        System.out.println("WorkerTwo doSomething");
    }
    @Override
    void doSomething2() {
        System.out.println("WorkerTwo doSomething2");
    }
    @Override
    void doSomething3() {
        System.out.println("WorkerTwo doSomething3");
    }
}
