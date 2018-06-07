package com.dragon.pattern.abstractFactory;

/**
 * Created by zhushuanglong on 2017/12/13.
 */
public class Factory_64g extends IphoneFactory{

    @Override public Iphone8 iphone8() {
        return new Iphone8_64g();
    }

    @Override public IphoneX iphonx() {
        return new IphoneX_64g();
    }
}
