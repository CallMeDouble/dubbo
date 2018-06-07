package com.dragon.pattern.StatusPattern;

/**
 * Created by zhushuanglong on 2018/4/9.
 */
public class Context {
    public static final OpenStatus OPEN_STATUS = new OpenStatus();
    public static final CloseStatus CLOSE_STATUS = new CloseStatus();
    public static final RunningStatus RUNNING_STATUS = new RunningStatus();
    public static final StopStatus STOP_STATUS = new StopStatus();

    private LiftStatus liftStatus;

    public LiftStatus getLiftStatus(){
        return liftStatus;
    }

    public void setLiftStatus(LiftStatus liftStatus){
        this.liftStatus = liftStatus;
        this.liftStatus.setContext(this);
    }

    public void open(){
        this.liftStatus.open();
    }

    public void close(){
        this.liftStatus.close();
    }

    public void run(){
        this.liftStatus.run();
    }

    public void stop(){
        this.liftStatus.stop();
    }
}
