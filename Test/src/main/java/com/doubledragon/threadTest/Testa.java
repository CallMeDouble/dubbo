package com.doubledragon.threadTest;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by double on 17-9-11.
 */
public class Testa {
    public static void main(String[] args) {
        Test test = new Test();
        test.gets().add("b");
        for(String s : test.gets()){
            System.out.println(s);
        }
        test.notify();
    }
}
