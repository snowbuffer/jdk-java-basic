package com.snowbuffer.study.java.sort;

import java.util.Arrays;

/**
 * Description: 插入排序： 从第二个元素开始，假定第二个元素之前的所有元素都是有序的，拿当前元素与该元素之前的所有元素进行比较，找到
 * 待插入的index后,先将index到当前元素index范围的元素全部后移index+1位，然后将待插入的元素插入到之前找到的index上
 *
 * @author cjb
 * @since 2020-04-14 23:56
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {6,5,4,3,2,1}; // 从小到大排序

        // 第一轮：假定第二个元素为需要排序的元素  5与 [6] 进行比较
        int needSortValue = arr[1];
        int needSortInsertIndex = 1 - 1;

        while (needSortInsertIndex >=0 && needSortValue < arr[needSortInsertIndex]) {
            arr[needSortInsertIndex + 1] = arr[needSortInsertIndex]; // 后移一位
            needSortInsertIndex--;
        }

        arr[needSortInsertIndex + 1] = needSortValue;

        System.out.println(Arrays.toString(arr));

        // 第二轮：假定第二个元素为需要排序的元素  4与 [5,6] 进行比较
        needSortValue = arr[2];
        needSortInsertIndex = 2 - 1;

        while (needSortInsertIndex >=0 && needSortValue < arr[needSortInsertIndex]) {
            arr[needSortInsertIndex + 1] = arr[needSortInsertIndex]; // 后移一位
            needSortInsertIndex--;
        }

        arr[needSortInsertIndex + 1] = needSortValue;

        System.out.println(Arrays.toString(arr));

        // 第三轮：假定第三个元素为需要排序的元素  3与 [4,5,6] 进行比较
        needSortValue = arr[3];
        needSortInsertIndex = 3 - 1;

        while (needSortInsertIndex >=0 && needSortValue < arr[needSortInsertIndex]) {
            arr[needSortInsertIndex + 1] = arr[needSortInsertIndex]; // 后移一位
            needSortInsertIndex--;
        }

        arr[needSortInsertIndex + 1] = needSortValue;

        System.out.println(Arrays.toString(arr));

        // 第四轮：假定第四个元素为需要排序的元素  2与 [3,4,5,6] 进行比较
        needSortValue = arr[4];
        needSortInsertIndex = 4 - 1;

        while (needSortInsertIndex >=0 && needSortValue < arr[needSortInsertIndex]) {
            arr[needSortInsertIndex + 1] = arr[needSortInsertIndex]; // 后移一位
            needSortInsertIndex--;
        }

        arr[needSortInsertIndex + 1] = needSortValue;

        System.out.println(Arrays.toString(arr));

        // 第五轮：假定第五个元素为需要排序的元素  1与 [2,3,4,5,6] 进行比较
        needSortValue = arr[5];
        needSortInsertIndex = 5 - 1;

        while (needSortInsertIndex >=0 && needSortValue < arr[needSortInsertIndex]) {
            arr[needSortInsertIndex + 1] = arr[needSortInsertIndex]; // 后移一位
            needSortInsertIndex--;
        }

        arr[needSortInsertIndex + 1] = needSortValue;

        System.out.println(Arrays.toString(arr));

        System.out.println("插入排序");
        int[] newArr = {6,5,4,3,2,1}; // 从小到大排序
        for (int i = 1; i < newArr.length; i++) {
            needSortValue = newArr[i];
            needSortInsertIndex = i - 1;
            while (needSortInsertIndex >=0 && needSortValue < newArr[needSortInsertIndex]) {
                newArr[needSortInsertIndex + 1] = newArr[needSortInsertIndex]; // 后移一位
                needSortInsertIndex--;
            }

            newArr[needSortInsertIndex + 1] = needSortValue;
        }
        System.out.println("插入排序结果：" + Arrays.toString(newArr));
    }
}
