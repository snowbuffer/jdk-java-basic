package com.wangwenjun.concurrency.chapter6.my;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-20 18:05
 */
public class MyThreadServiceMain {

    /**
     * 允许最大次数执行任务，如果任务执行超时，强制stop掉，并回收操作系统句柄 <br/>
     * 本方法执行一个任务，会开启三个线程，分别如下： <br/>
     * <li>currentThread: 执行任务的父线程</li>
     * <li>executeThread: 示例的业务线程</li>
     * <li>shutdownThread: 关闭currentThread线程</li>
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MyThreadService service = new MyThreadService(5);
        service.submit(() -> {
            try {
                System.out.println("#main 开始执行任务");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("#main 任务执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown(1_000);
//        Thread.currentThread().join();

    }

}

