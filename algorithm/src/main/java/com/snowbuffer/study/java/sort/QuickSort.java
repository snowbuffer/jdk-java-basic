package com.snowbuffer.study.java.sort;

import java.util.Arrays;

/**
 * 快速排序： 选中一个基础元素作为基准点，如果按从小到大排序，从左侧找到比基准元素大的数据，从右边找到比基准数据小的元素，并进行交换
 *  当一个基准点左侧元素均小小于基准点右侧元素，那么从重新在左侧找一个基准点，重新再右侧找一个基准点，递归排序
 *
 *  -9,78,0,23, [-567] ,70, -1,900, 4561
 *            ↑↑↑基准点↑↑↑
 *           ↓↓↓第一次排序后↓↓↓
 *  -567, 78, 0, 23, [-9], 70, -1, 900, 4561
 *                ↑↑↑基准点↑↑↑
 *                ↓↓↓第二次排序后↓↓↓
 *  -567, -9, 0, 23, 78, [70], -1, 900, 4561
 *                    ↑↑↑基准点↑↑↑
 *                    ↓↓↓第三次排序后↓↓↓
 *  -567, -9, 0, [23], -1, 70, 78, 900, 4561
 *            ↑↑↑基准点↑↑↑
 *            ↓↓↓第四次排序后↓↓↓
 *  -567, -9, [0], -1, 23, 70, 78, 900, 4561
 *         ↑↑↑基准点↑↑↑
 *         ↓↓↓第五次排序后↓↓↓
 *  -567, -9, -1, 0, 23, 70, 78, [900], 4561
 *                             ↑↑↑基准点↑↑↑
 *                             ↓↓↓第六次排序后↓↓↓
 *  -567, -9, -1, 0, 23, 70, 78, 900, 4561
 *
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {-9,78,0,23,-567,70, -1,900, 4561};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("arr=" + Arrays.toString(arr));
    }

    // 从小到大排序
    public static void quickSort(int[] arr, int left, int right) {
        int l = left; //左下标
        int r = right; //右下标
        //baseElement 中轴值
        int baseElement = arr[(left + right) / 2];
        System.out.println(left + "；" + baseElement + ";" + right);
        int temp = 0; //临时变量，作为交换时使用
        //while循环的目的是让比baseElement 值小放到左边
        //比pivot 值大放到右边
        while (l < r) {
            //在baseElement的左边一直找,找到大于等于baseElement值,才退出
            while (arr[l] < baseElement) {
                l += 1;
            }
            //在baseElement的右边一直找,找到小于等于baseElement值,才退出
            while (arr[r] > baseElement) {
                r -= 1;
            }
            //如果l >= r说明baseElement 的左右两的值，已经按照左边全部是
            //小于等于baseElement值，右边全部是大于等于baseElement值
            if (l >= r) {
                break;
            }

            //交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            System.out.println("arr=" + Arrays.toString(arr));
            //如果交换完后，发现这个arr[l] == baseElement值 相等 r--， 前移
            if (arr[l] == baseElement) {
                r -= 1;
            }
            //如果交换完后，发现这个arr[r] == baseElement值 相等 l++， 后移
            if (arr[r] == baseElement) {
                l += 1;
            }
        }

        // 如果 l == r, 必须l++, r--, 否则为出现栈溢出
        if (l == r) {
            l += 1;
            r -= 1;
        }
        //向左递归
        if (left < r) {
            quickSort(arr, left, r);
        }
        //向右递归
        if (right > l) {
            quickSort(arr, l, right);
        }


    }
}