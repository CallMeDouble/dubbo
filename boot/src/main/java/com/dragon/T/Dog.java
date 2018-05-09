package com.dragon.T;

/**
 * Created by dragon
 */
public class Dog extends Animal {
    public <T>  T find(Class<T> clazz, int id){
        return null;
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        Class<Cat> catClass = null;
        Cat o = dog.find(catClass, 1);
    }
}
