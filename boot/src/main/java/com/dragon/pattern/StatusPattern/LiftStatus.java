package com.dragon.pattern.StatusPattern;

/**
 * Created by zhushuanglong on 2018/4/9.
 */
public abstract class LiftStatus {
    protected Context context;

    public void setContext(Context context){
        this.context = context;
    }

    public abstract void open();
    public abstract void close();
    public abstract void run();
    public abstract void stop();
}
