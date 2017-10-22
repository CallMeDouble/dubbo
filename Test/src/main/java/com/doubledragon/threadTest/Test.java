package com.doubledragon.threadTest;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by double on 17-9-11.
 */
public final class Test {

    private final Set<String> s=new HashSet<String>();

    public Test(){
        s.add("a");
    }

    public Set<String> gets(){
        return s;
    }

}
