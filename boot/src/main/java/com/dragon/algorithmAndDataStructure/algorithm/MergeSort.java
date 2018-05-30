package com.dragon.algorithmAndDataStructure.algorithm;

import java.util.Arrays;

/**
 * Created by zhushuanglong on 2018/2/23.
 */
public class MergeSort {

//    public static void main(String []args){
//        int []arr = {9,8,7};
//        sort(arr);
//        System.out.println(Arrays.toString(arr));
//    }
//    public static void sort(int []arr){
//        int []temp = new int[arr.length];//在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
//        sort(arr,0,arr.length-1,temp);
//    }
//    private static void sort(int[] arr,int left,int right,int []temp){
//        if(left<right){
//            int mid = (left+right)/2;
//            sort(arr,left,mid,temp);//左边归并排序，使得左子序列有序
//            System.out.println("sort(arr,left,mid,temp):" + left + "," + mid);
//            sort(arr,mid+1,right,temp);//右边归并排序，使得右子序列有序
//            System.out.println("sort(arr,mid+1,right,temp):" + (mid+1) + "," + right);
//            merge(arr,left,mid,right,temp);//将两个有序子数组合并操作
//            System.out.println("merge(arr,left,mid,right,temp:" + left +","+mid +","+right );
//        }
//    }
//
//    //二路一次归并过程的算法
//    //low为本次二路归并排序的第1有序区的第1个元素，i指向第1个元素, mid为第1有序区的最后1个元素
//    void merge(int List[], int low, int mid, int high)
//    {
//        //mid+1为第2有序区第1个元素，mid为第1有序区的最后1个元素
//        //i 指向第 1 有序区的第 1 个元素
//        int i = low;
//        //j 指向第 2 有序区的第 1 个元素，high 为第 2 有序区的最后一个元素
//        int j = mid + 1;
//        //temp数组暂存合并的有序序列
//     int[] temp = new int[high - low + 1];
//        //设置临时数组的指示标志 k
//        int k = 0;
//        //内存分配失败
//        //顺序选取两个有序区的较小元素，存储到t数组中，因为是递增排序
//        while(i <= mid && j <= high){
//            //较小的元素，存入temp临时数组中
//            if(List[i] <= List[j]){
//                temp[k++] = List[i++];
//            }else{
//                temp[k++] = List[j++];
//            }
//        }// end of while
//        //比完之后，假如第1个有序区仍有剩余，则直接全部复制到 temp 数组
//        while(i <= mid){
//            temp[k++] = List[i++];
//        }
//        //比完之后，假如第2个有序区还有剩余，则直接全部复制到 temp 数组
//        while(j <= high){
//            temp[k++] = List[j++];
//        }
//        //将排好序的序列，重存回到 list 中 low 到 high 区间
//        for(i = low, k = 0; i <= high; i++, k++){
//            List[i] = temp[k];
//        }
//    }
//
//    //递归实现二路归并排序（也就是分治法的思想）
//    void mergeSort(int List[], int low, int high)
//    {
//        //二路归并排序，分为二路
//        int mid = (low + high) / 2;
//        //终止条件，low >= high， 不是while，且不含等号，否则死循环
//        if(low < high)
//        {
//            //递归过程，二路归并排序递归过程
//            mergeSort(List, low, mid);
//            mergeSort(List, mid + 1, high);
//            //归并
//            merge(List, low, mid, high);
//        }
//    }
//
//    void main()
//    {
//        int[] source=new int[]{49, 38, 65, 97, 76, 13, 27};
//
//        mergeSort(source, 0, 6);
//        mergeSort2(source, 0, 6, new int[source.length]);
//
//        for (int i = 0; i < 7; i++) {
//            System.out.println(Arrays.toString(source));
//        }
//
//    }
//



    public static void main(String []args){
        int[] arr = {49, 38,97, 65, 76, 13, 27};
        mergeSort2(arr, 0, arr.length-1, new int[arr.length]);
    }


    public static void  mergeSort2(int[] source, int low,int high, int[] tmp){
        //当序列被拆分到只有一个元素时，停止拆分。
        if(low<high){
            //二路归并排序，分为两路
            int mid = (low+high)/2;
            mergeSort2(source, low, mid, tmp);
            mergeSort2(source, mid+1, high, tmp);
            //合并每路对应的左右序列
            merge2(source, low, mid, high, tmp);
            System.out.println("合并结果："+Arrays.toString(source));
        }
    }

    public static void  merge2(int[] source, int low, int mid, int high, int[] tmp){
        int i = low;//左序列起始位置
        int j = mid+1;//右序列起始位置
        int k= 0;//临时序列索引位置

        //循环对比，直到左边或者右边的其中一个序列全部复制到tmp中
        while(i<=mid && j<=high){
            //对比左右序列中的元素，小的那个元素复制到tmp中，并且小的那个元素的索引位置右移一位
            if(source[i]<source[j]){
                tmp[k++]=source[i++];
            }else{
                tmp[k++]=source[j++];
            }
        }

        //将剩余的序列全部拷贝到tmp中
        while(i<=mid){
            tmp[k++]=source[i++];
        }

        //将剩余的序列全部拷贝到tmp中
        while(j<=high){
            tmp[k++]=source[j++];
        }

        k=0;
        while(low<=high){
            source[low++] = tmp[k++];
        }
    }



}
