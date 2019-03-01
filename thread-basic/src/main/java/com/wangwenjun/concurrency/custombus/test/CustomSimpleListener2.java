package com.wangwenjun.concurrency.custombus.test;

import com.wangwenjun.concurrency.custombus.CustomSubscribe;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 16:46
 */
public class CustomSimpleListener2 {

    @CustomSubscribe
    public void test1(String x) {
        System.out.println("CustomSimpleListener2===test1==[" + Thread.currentThread().getName() + "]" + x);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CustomSimpleListener2===test1==[" + Thread.currentThread().getName() + "] execute finished");
    }

    @CustomSubscribe
    public void test4(String x) {
        System.out.println("CustomSimpleListener2===test4==[" + Thread.currentThread().getName() + "]" + x);
    }

    @CustomSubscribe(topic = "alex-topic")
    public void test2(Integer x) {
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CustomSimpleListener2===test2==[" + Thread.currentThread().getName() + "]" + x);
    }

    @CustomSubscribe(topic = "test-topic")
    public void test3(Integer x) {
        throw new RuntimeException();
    }
}

