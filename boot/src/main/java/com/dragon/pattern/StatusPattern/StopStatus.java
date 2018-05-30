package com.dragon.pattern.StatusPattern;

/**
 * Created by zhushuanglong on 2018/4/9.
 */
public class StopStatus extends LiftStatus {
    @Override
    public void open() {
        super.context.setLiftStatus(Context.OPEN_STATUS);
        super.context.getLiftStatus().open();
    }

    @Override
    public void close() {
        super.context.setLiftStatus(Context.CLOSE_STATUS);
        super.context.getLiftStatus().close();
    }

    @Override
    public void run() {
        super.context.setLiftStatus(Context.RUNNING_STATUS);
        super.context.getLiftStatus().run();
    }

    @Override
    public void stop() {
        System.out.println("电梯停了");
    }
}
