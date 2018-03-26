package com.dragon.algorithmAndDataStructure.algorithm;

/**
 * Created by zhushuanglong on 2018/2/22.
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] array = new int[] { 20, 40, 30, 10, 60, 10, 50 };

        //第一层循环决定排序区间，假定第一个元素作为一开始的有序表, 则排序区间从第二个元素开始
        for (int i = 1; i < array.length; i++) {
            // 目标元素：无序表中需要被排序的元素，即排序区间的第一个元素
            int target = array[i];

            // j为目标元素要正确摆放的位置，初始值设为即排序区间的第一个元素的位置。因为是从这里开始和前面的有序表开始比较的。
            int j = i;

            // 如果目标元素比前一个元素小，（j-1为前一个元素，j为当前元素）
            while (j > 0 && target < array[j - 1]) {
                // 则当前位置设为前一个元素的值
                array[j] = array[j - 1];
                // 重复此过程，让目标元素和更前面的一个元素比较,此时，前一个元素变成了当前元素
                j--;
            }
            //如果目标元素大于等于前一个元素，则当前元素（j）设为目标值
            array[j] = target;
        }

        System.out.println(array);
        // 总结：插入排序非常类似于整扑克牌。把整个数组看成是两部分组成，一部分是有序的，一部分是无序的，排序过程中每次从无序表中取出第一个元素，
        // 依次和它前面的元素比较，找出合适的位置。


    }
}
