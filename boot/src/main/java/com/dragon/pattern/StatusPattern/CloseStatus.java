package com.dragon.pattern.StatusPattern;

/**
 * Created by zhushuanglong on 2018/4/9.
 * 当前电梯状态为关闭状态
 */
public class CloseStatus extends LiftStatus {
    //关闭状态时，是可以打开的
    @Override
    public void open() {
        super.context.setLiftStatus(Context.OPEN_STATUS);
        super.context.getLiftStatus().open();
    }

    //其他状态转换成关闭状态时，要执行的逻辑操作
    @Override
    public void close() {
        System.out.println("电梯关闭。。。");
    }

    //关闭状态是可以运行的
    @Override
    public void run() {
        super.context.setLiftStatus(Context.RUNNING_STATUS);
        super.context.getLiftStatus().run();
    }

    //关闭状态是可以停止的
    @Override
    public void stop() {
        super.context.setLiftStatus(Context.STOP_STATUS);
        super.context.getLiftStatus().stop();
    }
}
