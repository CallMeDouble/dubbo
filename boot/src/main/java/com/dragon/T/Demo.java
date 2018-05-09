package com.dragon.T;

/**
 * Created by dragon
 */
public class Demo<T extends Animal> {
    private T animal;
    public Demo(T animal) {
        this.animal = animal;
    }

    public T getAnimal() {
        return animal;
    }



    public void setAnimal(T animal) {
        this.animal = animal;
    }

    public void print(){
        System.out.println("T的类型是："+ animal.getClass().getName());
    }
}
