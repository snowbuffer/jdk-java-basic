package com.wangwenjun.concurrent.chapter4.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 11:47
 */
public class MyThreadLifeCycleObserver implements MyLifeCycleListener {

    private final ExecutorService service = Executors.newFixedThreadPool(10);

    public void concurrentAction(Runnable task) {
        service.execute(new MyObservableRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyEvent(new ThreadEvent(Thread.currentThread(), ThreadState.RUNNING, null));
                    task.run();
                    Thread.sleep(1000L);
                    notifyEvent(new ThreadEvent(Thread.currentThread(), ThreadState.DEAD, null));
                } catch (Exception e) {
                    notifyEvent(new ThreadEvent(Thread.currentThread(), ThreadState.ERROR, e));
                }
            }
        });
    }

    public void shutdown() {
        service.shutdown();
    }

    @Override
    public void publishEvent(MyObservableRunnable.ThreadEvent threadEvent) {
        System.out.println("The runnable [" + threadEvent.getThread().getName() + "] data changed and state is [" + threadEvent.getThreadState() + "]");
        if (threadEvent.getThrowable() != null) {
            System.out.println("The runnable [" + threadEvent.getThread().getName() + "] process failed.");
            threadEvent.getThrowable().printStackTrace();
        }
    }
}

