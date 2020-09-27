package com.dragon.pattern.Singleton;

public class LazySingleton {

    private static LazySingleton instance;

    public LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {

            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
