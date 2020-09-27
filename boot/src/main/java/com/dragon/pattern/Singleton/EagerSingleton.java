package com.dragon.pattern.Singleton;

public class EagerSingleton {

    private static final EagerSingleton instance = new EagerSingleton();

    public EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return instance;
    }
}
