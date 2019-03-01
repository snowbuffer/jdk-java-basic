package com.wangwenjun.concurrency.custombus;

import java.util.concurrent.Executor;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 17:05
 */
public class CustomAsyncEventBus extends CustomBusImpl {

    public CustomAsyncEventBus(Executor executor) {
        this("default-async", null, executor);
    }

    public CustomAsyncEventBus(String busName, Executor executor) {
        this("default-async", null, executor);
    }

    public CustomAsyncEventBus(CustomEventExceptionHandler commExceptionHandler, Executor executor) {
        this("default-async", commExceptionHandler, executor);
    }

    public CustomAsyncEventBus(String busName, CustomEventExceptionHandler commExceptionHandler, Executor executor) {
        super(busName, commExceptionHandler, executor);
    }
}

