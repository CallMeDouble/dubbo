package com.dragon.pattern.Command;

/**
 * Created by zhushuanglong on 2018/3/21.
 */
public class Client {
    public static void main(String[] args) {
        Worker workerOne = new WorkerOne();
        Command commandOne = new CommandOne(workerOne);
        Invoker invoker = new Invoker(commandOne);
        invoker.action();
    }
}
