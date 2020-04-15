package com.snowbuffer.study.java.sort;

import java.util.Arrays;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-04-15 22:01
 */
public class ShellSortForMove {

    public static void main(String[] args) {
        System.out.println("希尔排序[交换法-插入排序]");
        int[] newArray = {8, 9, 1, 7, 2, 3, 5, 4, 11, 6, 0};
        int temp = 0;
        for (int group = newArray.length / 2; group >= 1; group = group / 2) {
            for (int i = group/*组数*/; i < newArray.length; i++) {
                int needSortValue = newArray[i];
                int needSortInsertIndex = i - group;
                while (needSortInsertIndex >= 0 && needSortValue < newArray[needSortInsertIndex]) {
                    newArray[needSortInsertIndex + group] = newArray[needSortInsertIndex]; // 后移一位
                    needSortInsertIndex = needSortInsertIndex - group;
                }
                newArray[needSortInsertIndex + group] = needSortValue;
            }
        }
        System.out.println("希尔排序[交换法]结果：" + Arrays.toString(newArray));
    }
}
