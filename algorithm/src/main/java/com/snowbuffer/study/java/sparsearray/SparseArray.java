package com.snowbuffer.study.java.sparsearray;

/**
 * Description:  稀疏数组 <br/>
 * 第一行： 总行数                         总列数                     有效值数量 <br/>
 * 第二行： 有效值所在行(按index算)     有效值所在列(按index算)            有效值 <br/>
 * 第三行： 同第二行 <br/>
 *
 * @author cjb
 * @since 2020-04-06 23:26
 */
public class SparseArray {

    public static void main(String[] args) {
        int rowCount = 11;
        int colCount = 11;

        // 设置原始棋盘
        int[][] srcArray = new int[rowCount][colCount];
        srcArray[1][1] = 1;
        srcArray[2][2] = 2;

        System.out.println("原始srcArray => ");
        printArray(rowCount, colCount, srcArray);
        System.out.println("<= 原始srcArray");

        // 查找有效值总数
        int validValueSum = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (srcArray[i][j] != 0) {
                    validValueSum++;
                }
            }
        }
        System.out.println();
        System.out.println("srcArray 有效值总数 =>" + validValueSum);
        System.out.println();

        int[][] sparseArray = new int[validValueSum + 1][3];
        // 设置稀疏数组第一行信息：总行数，总列数，有效值总数
        sparseArray[0][0] = rowCount;
        sparseArray[0][1] = colCount;
        sparseArray[0][2] = validValueSum;

        // 设置有效值的位置信息
        int count = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (srcArray[i][j] != 0) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = srcArray[i][j];
                }
            }
        }
        System.out.println("sparseArray => ");
        for (int i = 0; i < sparseArray.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d\t", sparseArray[i][j]);
            }
            System.out.println();
        }
        System.out.println("<= sparseArray");

        // 从稀疏数组恢复成原始数组
        int[][] newSrcArray = new int[sparseArray[0][0]][sparseArray[0][1]];
        for (int i = 1; i < sparseArray.length; i++) {
            newSrcArray[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        System.out.println();
        System.out.println("恢复srcArray => ");
        printArray(rowCount, colCount, newSrcArray);
        System.out.println("<= 恢复srcArray");
    }

    private static void printArray(int rowCount, int colCount, int[][] targetArray) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                System.out.printf("%d\t", targetArray[i][j]);
            }
            System.out.println();
        }
    }
}
