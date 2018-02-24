package com.dragon.algorithm;

import java.util.Arrays;

/**
 * 快速排序：Java
 *
 * @author skywang
 * @date 2014/03/11
 */

public class QuickSort {
//    快速排序算法的基本思想是：
//    1．先从数列中取出一个数作为基准数。(这里取第一个数)
//    2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
//    3．再对左右区间重复第二步，直到各区间只有一个数。
    public static void quickSort(int[] arr, int left, int right){
        if(left < right){ // 判断快排序列是否为空
            int i = left; //定义筛选时左边的索引
            int j = right; //定义筛选时右边的索引
            int x = arr[i]; //取基数为第一个数

            //当i==j时，证明左右两边都已经分区完成
            while(i<j){

                //①先从右往左找出第一个小于等于x的数
                while(i<j && arr[j] >= x){
                    j--; //如果大于x则索引往前移动
                }
                if(i<j){ //到这一步说明已经找到了小于x的数，则将这个数赋值到前面去
                    arr[i] = arr[j];
                    i++; //左边的一个数已经替换完成，所以往后移动索引,之后的筛选从i之后的索引开始
                }

                //②然后从左往后找出大于x的数
                while(i<j && arr[i] < x){
                    i++; // 如果小于x则索引往后移动
                }
                if(i<j){ //到这一步说明已经找到了大于x的数，则将这个数赋值到后面去
                    arr[j] = arr[i];
                    j--; //右边的一个数也替换完成了，所以往前移动索引，之后筛选从j之前的所以开始
                }
            }

            //到了这里已经说明i==j了，此时所有的数已经正确放在了x的两边
            arr[i] = x;

            quickSort(arr, left, i-1);//快排x左边的序列
            quickSort(arr, i+1, arr.length-1);//快排x右边的序列
        }
    }


    public static void main(String[] args) {
        int[] arr = {30,40,60,10,20,50};
        quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}