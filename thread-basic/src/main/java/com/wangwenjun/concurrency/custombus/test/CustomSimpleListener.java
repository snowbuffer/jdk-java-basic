package com.wangwenjun.concurrency.custombus.test;

import com.wangwenjun.concurrency.custombus.CustomSubscribe;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 16:46
 */
public class CustomSimpleListener {

    @CustomSubscribe
    public void test1(String x) {
        System.out.println("CustomSimpleListener===test1==" + x);
    }

    @CustomSubscribe(topic = "alex-topic")
    public void test2(Integer x) {
        System.out.println("CustomSimpleListener===test2==" + x);
    }
}

