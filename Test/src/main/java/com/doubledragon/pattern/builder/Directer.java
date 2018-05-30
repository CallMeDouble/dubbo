package com.doubledragon.pattern.builder;

/**
 * Created by zhushuanglong on 2017/12/7.
 */
public class Directer {
    private Builder builder = new ConcreteBuilder();

    public Product getAProduct(){
        builder.build("A", "a");
        return builder.getProduct();
    }

    public Product getBProduct(){
        builder.build("B", "b");
        return builder.getProduct();
    }
}
