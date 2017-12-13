package com.doubledragon.pattern.Decorator;

/**
 * Created by zhushuanglong on 2017/12/13.
 */
public class GreenDecorator extends Decorator {
    public GreenDecorator(Component man) {
        super(man);
    }

    public void green(){
        System.out.println("刷上绿色的漆");
    }

    @Override public void decorate() {
        super.decorate();
        green();

    }
}
