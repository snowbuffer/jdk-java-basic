package com.snowbuffer.study.java.sort;

import java.util.Arrays;

/**
 * Description: 选择排序，先假定一个元素是最小的，然后遍历数组，当发现存在比假定元素还要小的时候，进行替换
 *
 * @author cjb
 * @since 2020-04-14 23:30
 */
public class SelectSort {

    public static void main(String[] args) {


        int[] arr = {6, 5, 4, 3, 2, 1}; // 从小到大排序

        // 第一轮： 假定第一个元素为最小
        int minIndex = 0;
        int min = arr[0];
        for (int i = 1 + 0; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
                minIndex = i;
            }
        }
        if (minIndex != 0) {
            arr[minIndex] = arr[0];
            arr[0] = min;
        }
        System.out.println(Arrays.toString(arr));

        // 第二轮： 假定第二个元素为最小
        minIndex = 1;
        min = arr[1];
        for (int i = 1 + 1; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
                minIndex = i;
            }
        }
        if (minIndex != 1) {
            arr[minIndex] = arr[1];
            arr[1] = min;
        }
        System.out.println(Arrays.toString(arr));

        // 第三轮： 假定第三个元素为最小
        minIndex = 2;
        min = arr[2];
        for (int i = 1 + 2; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
                minIndex = i;
            }
        }
        if (minIndex != 2) {
            arr[minIndex] = arr[2];
            arr[2] = min;
        }
        System.out.println(Arrays.toString(arr));

        // 第四轮： 假定第四个元素为最小
        minIndex = 3;
        min = arr[3];
        for (int i = 1 + 3; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
                minIndex = i;
            }
        }
        if (minIndex != 3) {
            arr[minIndex] = arr[3];
            arr[3] = min;
        }
        System.out.println(Arrays.toString(arr));

        // 第五轮： 假定第五个元素为最小
        minIndex = 4;
        min = arr[4];
        for (int i = 1 + 4; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
                minIndex = i;
            }
        }
        if (minIndex != 4) {
            arr[minIndex] = arr[4];
            arr[4] = min;
        }
        System.out.println(Arrays.toString(arr));

        System.out.println("选择排序");
        int[] newArr = {6, 5, 4, 3, 2, 1}; // 从小到大排序
        for (int j = 0; j < newArr.length - 1; j++) {
            minIndex = j;
            min = newArr[j];
            for (int i = 1 + j; i < newArr.length; i++) {
                if (min > newArr[i]) {
                    min = newArr[i];
                    minIndex = i;
                }
            }
            if (minIndex != j) {
                newArr[minIndex] = newArr[j];
                newArr[j] = min;
            }
        }
        System.out.println("选择排序结果：" + Arrays.toString(arr));
    }
}
