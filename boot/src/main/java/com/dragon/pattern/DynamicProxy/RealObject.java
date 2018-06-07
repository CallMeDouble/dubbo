package com.dragon.pattern.DynamicProxy;

/**
 * Created by dragon
 */
public class RealObject implements ISubjectObject{

    @Override
    public void test() {
        System.out.println("RealObject test");
    }

    @Override
    public void test2() {
        System.out.println("RealObject test2");
    }
}
