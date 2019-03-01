package com.wangwenjun.concurrent.chapter4.my;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 11:41
 */
public abstract class MyObservableRunnable implements Runnable {

    private MyLifeCycleListener myLifeCycleListener;

    public MyObservableRunnable(MyLifeCycleListener myLifeCycleListener) {
        this.myLifeCycleListener = myLifeCycleListener;
    }

    protected void notifyEvent(MyObservableRunnable.ThreadEvent threadEvent) {
        myLifeCycleListener.publishEvent(threadEvent);
    }

    public enum ThreadState {
        RUNNING, ERROR, DEAD;
    }

    public class ThreadEvent {

        private Thread thread;

        private ThreadState threadState;

        private Throwable throwable;

        public ThreadEvent(Thread thread, ThreadState threadState, Throwable throwable) {
            this.thread = thread;
            this.threadState = threadState;
            this.throwable = throwable;
        }

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }

        public ThreadState getThreadState() {
            return threadState;
        }

        public void setThreadState(ThreadState threadState) {
            this.threadState = threadState;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }
    }

}

