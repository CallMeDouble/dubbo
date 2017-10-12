package com.doubledragon.pattern.observer;

/**
 * Created by zhushuanglong on 2017/4/11.
 * 微信用户是观察者，里面实现了抽象观察者的更新方法
 */
public class WeixinUser implements Observer {
    //微信用户名
    private String name;

    public WeixinUser(String name) {
        this.name = name;
    }

    @Override public void update(String message) {
        System.out.print(name + "--" + message);
    }
}
