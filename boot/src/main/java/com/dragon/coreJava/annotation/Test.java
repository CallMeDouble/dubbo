package com.dragon.coreJava.annotation;

import java.lang.reflect.Method;

/**
 * Created by dragon
 */
public class Test {
    @TestAnnotation
    public static void test(){
        System.out.println("test");
    }
    public static void main(String[] args) throws NoSuchMethodException {
        Method test = Test.class.getDeclaredMethod("test", null);
        TestAnnotation annotation = test.getAnnotation(TestAnnotation.class);


    }
}
