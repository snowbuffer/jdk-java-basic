package com.wangwenjun.concurrency.custombus;

import java.util.concurrent.Executor;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 15:36
 */
public class CustomBusImpl implements CustomBus {

    private final CustomRegistry registry = new CustomRegistry();

    private final CustomDispatcher dispatcher;

    private String busName;

    private final static String DEFAULT_BUS_NAME = "default";

    private final static String DEFAULT_TOPIC = "default-topic";

    public CustomBusImpl() {
        this(DEFAULT_BUS_NAME, null, CustomDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public CustomBusImpl(String busName) {
        this(busName, null, CustomDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public CustomBusImpl(CustomEventExceptionHandler commExceptionHandler) {
        this(DEFAULT_BUS_NAME, commExceptionHandler, CustomDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    CustomBusImpl(String busName, CustomEventExceptionHandler commExceptionHandler, Executor executor) {
        this.busName = busName;
        this.dispatcher = CustomDispatcher.newCustomDispatcher(commExceptionHandler, executor);
    }

    @Override
    public void register(Object subscriber) {
        registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC);
    }

    @Override
    public void post(Object event, String topic) {
        dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}

