package com.dragon.pattern.StatusPattern;

/**
 * Created by zhushuanglong on 2018/4/9.
 */
public class OpenStatus extends LiftStatus {

    @Override
    public void open() {
        System.out.println("打开电梯。。。");
    }

    @Override
    public void close() {
        super.context.setLiftStatus(Context.CLOSE_STATUS);
        super.context.getLiftStatus().close();
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }
}
