package com.wangwenjun.concurrent.chapter4.my;

import java.util.stream.IntStream;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 13:44
 */
public class MyThreadLifeCycleObserverTest {

    public static void main(String[] args) {
        MyThreadLifeCycleObserver observer = new MyThreadLifeCycleObserver();
        IntStream.rangeClosed(1, 5).forEach((i) -> {
            observer.concurrentAction(() -> {
                if (i == 2) {
                    throw new RuntimeException(Thread.currentThread().getName() + " => error");
                }
                System.out.println(Thread.currentThread().getName() + " i => " + i);
            });
        });
    }

}

