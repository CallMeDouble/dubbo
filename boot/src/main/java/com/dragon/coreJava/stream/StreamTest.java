package com.dragon.coreJava.stream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Created by dragon
 */
public class StreamTest {
    public static void main(String[] args) {
        HashSet<String> strings = new HashSet<>();
        strings.add("a");
        strings.add("aa");
        strings.stream().filter(e -> e.length()>1).collect(Collectors.toList());

        HashMap<String, String> stringStringHashMap = new HashMap<>();
    }
}
