package com.snowbuffer.study.java.common.jvm;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-03-06 14:29
 */
public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        // -Xms1024m -Xmx2048m -XX:+PrintGCDetails
        int cpu = Runtime.getRuntime().availableProcessors();
        long maxMemory = Runtime.getRuntime().maxMemory(); // -Xmx  不配置，默认物理内存的四分之一
        long totalMemory = Runtime.getRuntime().totalMemory(); // -Xms 不配置，默认物理内存的六十四分之一
        System.out.println("cpu=" + cpu);
        System.out.println("最大内存maxMemory=" + maxMemory / 1000 / 1000 + "mb");
        System.out.println("jvm中已使用的内存totalMemory=" + totalMemory / 1000 / 1000 + "mb");

        Thread.currentThread().join();
    }

//    public static void main(String[] args) {
//        byte[] bytes = new byte[1024 * 1024 * 20];
//    }
}
