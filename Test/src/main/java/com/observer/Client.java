package com.observer;

/**
 * Created by zhushuanglong on 2017/4/11.
 */
public class Client {
    public static void main(String[] args) {
        //比较概念的解释是，目标和观察者是基类，目标提供维护观察者的一系列方法，观察者提供更新接口。
        // 具体观察者和具体目标继承各自的基类，然后具体观察者把自己注册到具体目标里，在具体目标发生变化时候，调度观察者的更新方法。
        ConcreteSubject concreteSubject = new ConcreteSubject();
        //创建微信用户
        WeixinUser user1 = new WeixinUser("张三");
        WeixinUser user2 = new WeixinUser("李四");
        WeixinUser user3 = new WeixinUser("王五");
        //订阅公众号
        concreteSubject.attach(user1);
        concreteSubject.attach(user2);
        concreteSubject.attach(user3);
        //公众号更新发出消息给订阅的微信用户
        concreteSubject.notify("祝双龙的专栏更新了");

        char a='a';
        char b='b';
        String v="a";
        int c=999999999;
        int d=999999999;
        int e=c*d*c*c*c;
        System.out.print(e);
    }
}
