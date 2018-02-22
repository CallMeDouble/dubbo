package com.dragon.coreJava;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhushuanglong on 2018/2/22.
 */
public class Test {
    public static void main(String[] args) {
        int[] intArray = new int[]{1, 2, 3, 4};
        //List<int[]> ints = Arrays.asList(intArray); //无法使用该工具类转换
        List<Integer> integerList = Ints.asList(intArray); //这个list不是 java.util.list!!!
        //转换为java.util.list
        List<Integer> list = new ArrayList<>(integerList);


        //String数组转为List
        String[] stringArray = new String[]{"a", "b", "c"};
        List<String> stringList1 = Arrays.asList(stringArray); //这个list不是 java.util.list!!!
        //String数组转为List的最有效的方法
        List<String> stringList2 = new ArrayList<>(stringArray.length);
        Collections.addAll(stringList2, stringArray);
    }
}
