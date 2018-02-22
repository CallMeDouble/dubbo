package com.dragon.pattern.observer;

/**
 * Created by zhushuanglong on 2017/4/11.
 * 抽象被观察者（主题）
 */
public interface Subject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    void attach(Observer observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    void detach(Observer observer);

    /**
     * 通知订阅者
     * @param message
     */
    void notify(String message);
}
