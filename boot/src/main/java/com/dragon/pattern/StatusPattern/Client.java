package com.dragon.pattern.StatusPattern;

/**
 * Created by zhushuanglong on 2018/4/9.
 */
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.setLiftStatus(Context.CLOSE_STATUS);
        context.open();
        context.run();
        context.stop();
        context.close();
        context.run();
        context.stop();
        context.open();
    }
}
