package com.snowbuffer.study.java.common.thread.schedule;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-20 11:31
 */
public class ScheduleServiceTest {

    // 单个线程调度多种任务测试：始终串行，无论多种任务调度时间长度
    @Test
    public void testSchedule1() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable1(1),
                -1,
                2,
                TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable2(5),
                -1,
                2,
                TimeUnit.SECONDS);

        Thread.currentThread().join(30_000);

        /**
         *  始终串行，无论多种任务调度时间长度
         * getRunnable1 ==
         * getRunnable2 ==
         * getRunnable1 ==
         * getRunnable2 ==
         * getRunnable1 ==
         * getRunnable2 ==
         */
    }

    // 多个线程调度多种任务测试：并行执行， 每一种任务单独在自己的线程中执行，相互之间不影响
    @Test
    public void testSchedule2() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable1(1),
                -1,
                2,
                TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable2(5),
                -1,
                2,
                TimeUnit.SECONDS);

        Thread.currentThread().join(30_000);

        /**
         * 并行执行， 每一种任务单独在自己的线程中执行，相互之间不影响
         *
         * getRunnable1 ==
         * getRunnable1 ==
         * getRunnable2 ==
         * getRunnable1 ==
         * getRunnable1 ==
         * getRunnable1 ==
         * getRunnable2 ==
         */
    }

    // 任务取消(不中断目标线程)，底层直接将任务移除，但会保证任务执行完成
    @Test
    public void testTaskRemove1() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        ScheduledFuture<?> scheduledFuture1 = scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable1(1),
                -1,
                2,
                TimeUnit.SECONDS);

        ScheduledFuture<?> scheduledFuture2 = scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable2(1),
                -1,
                2,
                TimeUnit.SECONDS);

        ScheduleService.INSTANCE.cancelSchedule("scheduledFuture1", scheduledFuture1, false);

        Thread.currentThread().join(30_000);
    }

    // 任务取消(中断目标线程)，底层直接将任务移除，如果当前正在执行的任务存在Interrupt状态逻辑，会被强制打断
    @Test
    public void testTaskRemove2() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        ScheduledFuture<?> scheduledFuture1 = scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable1(1),
                -1,
                2,
                TimeUnit.SECONDS);

        ScheduledFuture<?> scheduledFuture2 = scheduledExecutorService.scheduleAtFixedRate(
                ScheduleService.INSTANCE.getRunnable2(1),
                -1,
                2,
                TimeUnit.SECONDS);

        ScheduleService.INSTANCE.cancelSchedule("scheduledFuture1", scheduledFuture1, true);

        Thread.currentThread().join(30_000);
    }
}