package com.wangwenjun.concurrency.custombus.test;

import com.wangwenjun.concurrency.custombus.CustomAsyncEventBus;

import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 16:47
 */
public class CustomEventBusExample {

    public static void main(String[] args) {
//        CustomBusImpl myEventBus = new CustomBusImpl((cause, context) ->
        CustomAsyncEventBus myEventBus = new CustomAsyncEventBus((cause, context) ->
        {
            cause.printStackTrace();
            System.out.println("==========================================");
            System.out.println(context.getSubscribe());
            System.out.println(context.getSource());
            System.out.println(context.getEvent());
            System.out.println(context.getSubscriber());
        }, Executors.newFixedThreadPool(4));
        myEventBus.register(new CustomSimpleListener2());
        myEventBus.post(123131, "alex-topic");
        myEventBus.post("123131aaa");
        myEventBus.close();
    }
}

