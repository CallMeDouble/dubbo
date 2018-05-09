package com.dragon.T;

/**
 * Created by dragon
 */
public class Test {

    public static void take1(Demo<Animal> demo){
        demo.print();
    }

    /*
    不能重载方法tabke1，因为泛型会被擦除
    public static void take1(Demo<Dog> demo){
        demo.print();
    }
    */

    public static void take2(Demo<?> demo){
        demo.print();
    }

    /*
    不能重载方法tabke2，因为泛型会被擦除
    public static void take2(Demo<? extends Animal> demo){
        demo.print();
    }
    */

    public static void main(String[] args) {
        Demo<Dog> dogDemo = new Demo<Dog>(new Dog());
        //这里报错，是因为take1方法中Demo支持的类型是Animal这个具体的类，而不是Dog类，所以报错
        //take1(dogDemo);
        take2(dogDemo);

        Demo<Cat> catDemo = new Demo<Cat>(new Cat());
        //这里报错，是因为take1方法中Demo支持的类型是Animal这个具体的类，而不是Cat类，所以报错
        //take1(catDemo);
        take2(dogDemo);
    }


}
