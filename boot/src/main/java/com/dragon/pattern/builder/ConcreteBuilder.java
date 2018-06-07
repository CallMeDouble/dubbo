package com.dragon.pattern.builder;

/**
 * Created by zhushuanglong on 2017/12/7.
 */
public class ConcreteBuilder extends Builder {
    private Product product = new Product();
    @Override
    public void build(String name, String type) {
        product.setName(null);
        product.setType(null);

        product.setName(name);
        product.setType(type);
    }

    @Override
    public Product getProduct() {
        return product;
    }
}
