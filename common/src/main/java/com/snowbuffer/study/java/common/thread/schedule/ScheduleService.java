package com.snowbuffer.study.java.common.thread.schedule;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-20 11:30
 */
public class ScheduleService {

    public final static ScheduleService INSTANCE = new ScheduleService();

    public Runnable getRunnable1(Integer seconds) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(seconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable1 executing ==");
            }
        };
    }

    public Runnable getRunnable2(Integer seconds) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(seconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable2 executing ==");
            }
        };
    }

    public boolean cancelSchedule(String scheduleTaskName, ScheduledFuture scheduledFuture, boolean mayInterruptIfRunning) throws InterruptedException {
        System.out.println("5s后开始取消任务:" + scheduleTaskName);
        TimeUnit.SECONDS.sleep(5);
        AtomicBoolean cancelOk = new AtomicBoolean(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                cancelOk.set(scheduledFuture.cancel(mayInterruptIfRunning));
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("取消结果: " + scheduleTaskName + " => " + cancelOk.get());
        return cancelOk.get();
    }

}
