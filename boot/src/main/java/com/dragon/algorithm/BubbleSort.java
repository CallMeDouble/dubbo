package com.dragon.algorithm;

/**
 * Created by zhushuanglong on 2018/2/22.
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] array = new int[]{20,40,30,10,60,50};
        //第一层循环决定每一趟的交换区间，交换区间从最大长度依次减到1，注意是0~length-1,否则会越界!!!
        for (int i=array.length-1; i>0; i--){
            //第二层循环就是在每一趟区间内，除了最后一个外，将第一个和后一个交换，否则就会越界因此是j<i ！！！
            for(int j=0; j<i; j++){
                if(array[j] > array[j+1]){
                    swap(array, j);
                    System.out.println(array);
                }
            }
        }
        System.out.println(array);
    }

    public static void swap(int[] array,int index){
        int temp;
        temp = array[index];
        array[index] = array[index+1];
        array[index+1] = temp;
    }
}
