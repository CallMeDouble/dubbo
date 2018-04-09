package com.dragon.pattern.StatusPattern;

/**
 * Created by zhushuanglong on 2018/4/9.
 */
public class RunningStatus extends LiftStatus {
    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void run() {
        System.out.println("电梯正在运行");
    }

    @Override
    public void stop() {
        super.context.setLiftStatus(Context.STOP_STATUS);
        super.context.getLiftStatus().stop();
    }
}
