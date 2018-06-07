package com.doubledragon.pattern.Decorator;

/**
 * Created by zhushuanglong on 2017/12/13.
 */
public class RedDecorator extends Decorator {
    public RedDecorator(Component man) {
        super(man);
    }

    public void red(){
        System.out.println("刷上红色的漆");
    }

    @Override public void decorate() {
        super.decorate();
        red();

    }
}
