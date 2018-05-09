package com.test;

/**
 * Created by zhushuanglong on 2017/8/10.
 */
public class Test {

    private static String string = null;

    private static void A() throws Exception{
        //System.out.println("a方法");
            B();
            System.out.println("A抛异常了");
    }

    private static void B() throws Exception{
        //System.out.println("b方法");
            C();
            System.out.println("B抛异常了");
    }

    private static void C(){
        //System.out.println("c方法");
        System.out.println("C抛异常了");
        string.getBytes();

    }

    public static void main(String[] args) throws Exception{
        Class<?> aClass = Class.forName("com.test.T");
        Object o = aClass.newInstance();
    }
}
