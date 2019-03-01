package com.wangwenjun.concurrent.chapter3;

/***************************************
 * @author:Alex Wang
 * @Date:2017/3/18 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class VolatileTest2 {

    private static volatile int INIT_VALUE = 0; // 只保证读取最新数据，但不保证原子性

    private final static int MAX_LIMIT = 500;

    public static void main(String[] args) {
        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT) {
                int localValue = INIT_VALUE;
                localValue++;
                INIT_VALUE = localValue;
                System.out.println("T1->" + localValue);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-1").start();

        new Thread(() -> {
            while (INIT_VALUE < MAX_LIMIT) {
                int localValue = INIT_VALUE;
                localValue++;
                INIT_VALUE = localValue;
                System.out.println("T2->" + localValue);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-1").start();
    }
}
