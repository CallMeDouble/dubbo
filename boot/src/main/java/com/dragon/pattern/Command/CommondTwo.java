package com.dragon.pattern.Command;

/**
 * Created by zhushuanglong on 2018/3/21.
 */
public class CommondTwo extends Command {
    private Worker receiverone;
    public CommondTwo(Worker worker) {
        this.receiverone = worker;
    }

    @Override
    public void execute() {
        this.receiverone.doSomething();
    }
}
