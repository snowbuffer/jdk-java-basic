package com.wangwenjun.concurrency.chapter6.my;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-20 17:52
 */
public class MyThreadService {

    private Thread currentThread;

    private boolean finished = false;

    private Runnable current;

    private int maxRetry;

    private int counter;

    public MyThreadService(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public void submit(Runnable task) {
        current = task;
        currentThread = new Thread(() -> {
            Thread executeThread = new Thread(task, "aaaaa");
            executeThread.setDaemon(true);
            executeThread.start();
            try {
                executeThread.join();
                finished = true;
            } catch (InterruptedException e) {
                System.out.println("#submit currentThread 被中断，并退出");
                executeThread.stop();  // 后台线程在jvm执行完成之后会自动强制退出，因此这里可以使用stop方法
                System.gc(); //  后台线程可能涉及到操作系统句柄操作，因此通知gc尽快回收
            }
        }, "bbbbb");
        currentThread.start();
    }

    public void shutdown(long mills) {
        Thread shutdownThread = new Thread(() -> doShutdown(mills), "shutdown");
        shutdownThread.start();
    }

    public void doShutdown(long mills) {
        long currentTime = System.currentTimeMillis();
        while (!finished) {
            if (System.currentTimeMillis() - currentTime >= mills) {
                System.out.println("#doShutdown currentThread 执行时间超时，进行中断操作");
                currentThread.interrupt();
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("#doShutdown 休眠被中断");
                break;
            }
        }

        if (!finished) {
            if (++counter > maxRetry) {
                throw new IllegalArgumentException("#doShutdown 任务已经达到最大重试次数：" + maxRetry);
            }

            try {
                System.out.println("#doShutdown 强制executeThread.stop后，休眠5s");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {
                System.out.println("#doShutdown 强制executeThread.stop后，休眠5s被中断");
            }
            System.out.println("#doShutdown 重新提交任务：counter => " + counter);
            submit(current);
            doShutdown(mills);
        }

        System.out.println("#doShutdown 任务执行总时间：" + (System.currentTimeMillis() - currentTime));

    }

}

