package com.dragon.pattern.Command;

/**
 * Created by zhushuanglong on 2018/3/21.
 */
public class CommandOne extends Command {
    private Worker receiverone;
    public CommandOne(Worker worker) {
        this.receiverone = worker;
    }

    @Override
    public void execute() {
        this.receiverone.doSomething();
        this.receiverone.doSomething2();
        this.receiverone.doSomething3();
    }
}
