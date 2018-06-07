package com.dragon.coreJava.stream;

/**
 * Created by zhushuanglong on 2018/2/24.
 */
public class StaticTest {
    private static int a;

//    public static void main(String[] args) {
//        modify(a);
//        System.out.println(a);
//    }
//
//    public static void  modify(int b){
//        a++;
//        System.out.println(b);
//    }

//    public static void main(String[] args) {
//        StaticTest t = new StaticTest();
//        int a=99;
//        t.test1(a);//这里传递的参数a就是按值传递
//        System.out.println(a);
//
//        MyObj obj=new MyObj();
//        t.test2(obj);//这里传递的参数obj就是引用传递
//        System.out.println(obj.b);
//    }
//
//    public void test1(int a){
//        a=a++;
//        System.out.println(a);
//    }
//
//    public void test2(MyObj obj){
//        obj.b=100;
//        System.out.println(obj.b);
//    }
//
//    static class MyObj{
//        public int b=99;
//    }

    public static void main(String[] args){
        StaticTest pk=new StaticTest();
        //String类似基本类型，值传递，不会改变实际参数的值
        String test1="Hello";
        pk.change(test1);
        System.out.println(test1);

        //StringBuffer和StringBuilder等是引用传递
        StringBuffer test2=new StringBuffer("Hello");
        pk.change(test2);

        System.out.println(test2.toString());
    }

    public void change(String str){
        str=str+"world";
    }

    public void change(StringBuffer str){
        str.append("world");
    }
}
