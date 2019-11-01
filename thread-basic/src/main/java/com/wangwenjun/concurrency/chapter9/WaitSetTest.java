package com.wangwenjun.concurrency.chapter9;

import java.util.concurrent.TimeUnit;

public class WaitSetTest {

    private static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                execute(this.getName());
            }
        };
        thread.setName("execute");
        thread.setPriority(1);
        thread.start();

        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                execute(this.getName());
            }
        };
        thread2.setPriority(10);
        thread2.setName("execute2");

        synchronized (obj) {
            thread2.start(); //
            Thread.sleep(1000);
            obj.notifyAll();
            System.out.println("=====");
        }

        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * thread => execute, start
     * thread => execute, 开始执行第一个wait
     * thread => execute2, start
     * =====
     * thread => execute2, 开始执行第一个wait  第一个waitset线程没有继续执行，就被后来的execute2给执行了，存在线程安全问题
     * thread => execute, 开始执行第二个wait
     */

    public static void execute(String threadName) {
        System.out.println("thread => " + threadName + ", start");
        synchronized (obj) {
            try {
                System.out.println("thread => " + threadName + ", 开始执行第一个wait");
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread => " + threadName + ", 开始执行第二个wait");
            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("thread => " + threadName + ", end");
    }
}
