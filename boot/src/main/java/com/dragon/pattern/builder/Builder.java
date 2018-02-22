package com.dragon.pattern.builder;

/**
 * Created by zhushuanglong on 2017/12/7.
 */
public abstract class Builder {
    public abstract void build(String name, String type);

    public abstract Product getProduct();
}
