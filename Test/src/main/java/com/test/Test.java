package com.test;

/**
 * Created by zhushuanglong on 2017/8/10.
 */
public class Test {



    public static void main(String[] args) {
//        List<String> l =new ArrayList<>();
//        l.add("1");
//        l.add("2");
//        List<Integer> list = l.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
//        for(Integer i : list){
//            System.out.println(i == 1);
//        }

            retry:// 1<span style="font-family: Arial, Helvetica, sans-serif;">（行2）</span>
            for (int i = 0; i < 10; i++) {
//                retry:// 2（行4）
                while (i == 5) {
                    continue retry;
                }
                System.out.print(i + " ");
        }
    }

}
