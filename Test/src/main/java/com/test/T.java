package com.test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhushuanglong on 2017/9/1.
 */
public class T {
    private  final Set<String> a=new HashSet<>();
    private final S s;

    public T() {
        a.add("a");
        a.add("c");
        a.add("d");

        s=new S();
    }

    public Set<String> getA() {
        return a;
    }

    public S getS() {
        return s;
    }
}
