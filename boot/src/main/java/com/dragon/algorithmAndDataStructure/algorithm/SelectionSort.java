package com.dragon.algorithmAndDataStructure.algorithm;

/**
 * Created by zhushuanglong on 2018/2/22.
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] array = new int[]{20,40,30,10,60,10,50};

        int min; //排序区间中的最小元素的位置
        //第一层循环决定每趟遍历区间，第一趟开始的位置为0，之后依次增加，直到区间内只剩下最后一个元素
        for(int i=0; i<array.length; i++){
            //每趟排序开始前，默认区间内第一个元素为最小值
            min = i;
            //上面的第一个元素作为最小值后，将它来和之后的元素进行比较，找出该趟区间内的最小的元素
            for(int j=i+1; j<array.length; j++){
                if(array[j] < array[min]){
                    min = j;
                }
            }
            //如果最小的元素不是区间内第一个元素了，就让它和区间内第一个元素交换。
            if(min != i){
                swap(array, i, min);
            }
        }
        System.out.println(array);
        //总结：每趟排序时，默认第一个数为区间内最小的数，和区间内之后的数比较后选择出最小的数，如果最小的数不是第一个，就和第一个交换。
        //     区间从第一个元素开始每趟往后挪。
    }


    public static void swap(int[] array,int i, int min){
        int temp;
        temp = array[i];
        array[i] = array[min];
        array[min] = temp;
    }
}
