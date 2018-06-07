package com.dragon.pattern.Prototype;

/**
 * Created by zhushuanglong on 2018/4/9.
 */
public class PrototypeClass implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return  (PrototypeClass) super.clone();
    }
}
