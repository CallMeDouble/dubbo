package com.doubledragon.pattern.builder;

/**
 * Created by zhushuanglong on 2017/12/7.
 */
public class Client {
    public static void main(String[] args) {
        Directer directer = new Directer();
        Product product = directer.getAProduct();
        product.showProduct();
        Product bProduct = directer.getBProduct();
        bProduct.showProduct();
    }
}
