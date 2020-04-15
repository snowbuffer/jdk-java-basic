package com.snowbuffer.study.java.sort;

import java.util.Arrays;

/**
 * Description: 冒泡排序: 相邻两个元素进行比较，如果前者大于后者，则进行元素交换
 *
 * @author cjb
 * @since 2020-04-14 22:59
 */
public class BubbleSort {


    public static void main(String[] args) {

        int[] arr = {6, 5, 4, 3, 2, 1};


        // 推演：
        /**
         * 第一趟排序
         * (1)  [6], [5],  4,   3,   2,   1
         * (2)   6,  [5], [4],  3,   2,   1
         * (3)   6,   5,  [4], [3],  2,   1
         * (4)   6,   5,   4,  [3], [2],  1
         * (5)   6,   5,   4,   3,  [2], [1]
         * 输出   5,   4,   3,   2,   1,   6 找到最大值 6
         *
         * 第二趟排序
         * (1)  [5], [4],  3,   2,   1,   6
         * (2)   5,  [4], [3],  2,   1,   6
         * (3)   5,   4,  [3], [2],  1,   6
         * (4)   5,   4,   3,  [2], [1],  6
         * 输出   4,   3,   2,   1,   5,   6 找到最大值 5
         *
         * 第三趟排序
         * (1)  [4], [3],  2,   1,   5,   6
         * (2)   4,  [3], [2],  1,   5,   6
         * (3)   4,   3,  [2], [1],  5,   6
         * 输出   3,   2,   1,   4,   5,   6 找到最大值 4
         *
         * 第四趟排序
         * (1)  [3], [2],  1,   4,   5,   6
         * (2)   3,  [2], [1],  4,   5,   6
         * 输出   2,   1,   3,   4,   5,   6 找到最大值 3
         *
         * 第五趟排序
         * (1)  [2], [1],  3,   4,   5,   6
         * 输出   1,   2,   3,   4,   5,   6 找到最大值 2
         */

        // 第一趟排序
        int temp = 0;
        for (int i = 0; i < arr.length - 1 - 0; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }

        System.out.println("第一趟排序结果：" + Arrays.toString(arr));

        // 第二趟排序
        temp = 0;
        for (int i = 0; i < arr.length - 1 - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }

        System.out.println("第二趟排序结果：" + Arrays.toString(arr));

        // 第三趟排序
        temp = 0;
        for (int i = 0; i < arr.length - 1 - 2; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }

        System.out.println("第三趟排序结果：" + Arrays.toString(arr));

        // 第四趟排序
        temp = 0;
        for (int i = 0; i < arr.length - 1 - 3; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }

        System.out.println("第四趟排序结果：" + Arrays.toString(arr));

        // 第五趟排序
        temp = 0;
        for (int i = 0; i < arr.length - 1 - 4; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }

        System.out.println("第五趟排序结果：" + Arrays.toString(arr));


        System.out.println("冒泡排序：");

        int[] newArr = {6, 5, 4, 3, 2, 1};
        temp = 0;
        boolean hasSwitch = false;
        for (int j = 0; j < newArr.length - 1; j++) {
            System.out.println(j);
            for (int i = 0; i < newArr.length - 1 - j; i++) {
                if (newArr[i] > newArr[i + 1]) {
                    hasSwitch = true;
                    temp = newArr[i];
                    newArr[i] = newArr[i + 1];
                    newArr[i + 1] = temp;
                }
            }
            if (!hasSwitch) {
                break;
            } else {
                hasSwitch = false;
            }
        }
        System.out.println("冒泡排序结果：" + Arrays.toString(newArr));
    }
}
