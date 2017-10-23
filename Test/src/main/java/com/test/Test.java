package com.test;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by zhushuanglong on 2017/8/10.
 */
public class Test {



    public static void main(String[] args) {
        List<String> l =new ArrayList<>();
        l.add("1");
        l.add("2");
        List<Integer> list = l.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
        for(Integer i : list){
            System.out.println(i == 1);
        }

        A a = new A();
        a.setA("a");
        Set<String> objects = new HashSet<>();
        objects.add("1");
        objects.add("2");
        a.setB(objects);
        System.out.println(JSON.toJSONString(a));
    }

}
