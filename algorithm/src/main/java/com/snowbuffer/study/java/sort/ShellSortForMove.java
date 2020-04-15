package com.snowbuffer.study.java.sort;

import java.util.Arrays;

/**
 * Description: 希尔排序(移动法)： 希尔排序是插入排序的优化算法(插入排序如果最小的值在最后，那么比较耗性能，因此待排序的元素分布越均匀越好)
 * 希尔排序： 通过将待排元素数组先按组分组，每分组一次，就对同组内的所有元素进行排序，直到所有元素又重新分到同一组为止：
 * int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 0}; // 10个元素
 * 第一轮： 分五组： 10 / 2 = 5 组
 * 对每一组的元素进行排序  此时每一组元素具有两个元素
 * 第二路： 对上一轮的 5 组 重新编排： 5 / 2 = 2 组
 * 对每一组的元素进行排序  此时每一组元素具有5个元素
 * 第二路： 对上一轮的 2 组 重新编排： 2 / 2 = 1 组
 * 对每一组的元素进行排序  此时每一组元素具有10个元素
 * <p>
 * 希尔排序的思想： 化整为零，再从零聚合到整体
 *
 * @author cjb
 * @since 2020-04-15 21:36
 */
public class ShellSortForMove {

    public static void main(String[] args) {
        System.out.println("希尔排序[移动法-插入排序]");
        int[] newArray = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
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
