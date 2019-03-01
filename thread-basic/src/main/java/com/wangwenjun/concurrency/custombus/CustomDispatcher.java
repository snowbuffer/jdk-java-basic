package com.wangwenjun.concurrency.custombus;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 15:40
 */
public class CustomDispatcher {

    static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;

    static final Executor PRE_THREAD_EXECUTOR_SERVICE = PerThreadExecutorService.INSTANCE;

    private final Executor executorService;

    private final CustomEventExceptionHandler exceptionHandler;

    public CustomDispatcher(Executor executor, CustomEventExceptionHandler commExceptionHandler) {
        this.executorService = executor;
        this.exceptionHandler = commExceptionHandler;
    }

    static CustomDispatcher newCustomDispatcher(CustomEventExceptionHandler commExceptionHandler, Executor executor) {
        return new CustomDispatcher(executor, commExceptionHandler);
    }

    static CustomDispatcher seqDispatcher(CustomEventExceptionHandler exceptionHandler) {
        return newCustomDispatcher(exceptionHandler, SEQ_EXECUTOR_SERVICE);
    }

    static CustomDispatcher perThreaDDispatcher(CustomEventExceptionHandler exceptionHandler) {
        return newCustomDispatcher(exceptionHandler, PRE_THREAD_EXECUTOR_SERVICE);
    }

    /**
     * 业务处理
     *
     * @param fromBus  来源总线
     * @param registry 注册表
     * @param event    发生的事件
     * @param toTopic  主体
     */
    public void dispatch(CustomBusImpl fromBus, CustomRegistry registry, Object event, String toTopic) {
        ConcurrentLinkedQueue<CustomSubscriber> customSubscribers = registry.scanSubscriber(toTopic);
        if (null == customSubscribers) {
            if (exceptionHandler != null) {
                exceptionHandler.handle(new IllegalArgumentException("The topic " + toTopic + " not bind yet"),
                        new DefaultCustomEventContext(fromBus.getBusName(), null, event));

                return;
            }
        }

        customSubscribers.stream().filter(item -> !item.getDisabled())
                .filter(item -> {
                    Method subscribeMethod = item.getSubscriberMethod();
                    Class<?> aClass = subscribeMethod.getParameterTypes()[0];
                    return (aClass.isAssignableFrom(event.getClass()));
                }).forEach(subscriber -> relealInvoke(subscriber, fromBus, event));
    }

    private void relealInvoke(CustomSubscriber subscriber, CustomBusImpl fromBus, Object event) {
        Method subscribeMethod = subscriber.getSubscriberMethod();
        Object subscribeObject = subscriber.getSubscriberObject();
        executorService.execute(() -> {
            try {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e) {
                if (null != exceptionHandler) {
                    exceptionHandler.handle(e, new DefaultCustomEventContext(fromBus.getBusName(), subscriber, event));
                }
            }
        });
    }

    /**
     * 关闭执行器
     */
    public void close() {
        if (executorService instanceof ExecutorService) {
            ((ExecutorService) executorService).shutdown();
        }
    }

    /**
     * 串行执行器
     */
    private static class SeqExecutorService implements Executor {

        public static final Executor INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    /**
     * 串行执行器
     */
    private static class PerThreadExecutorService implements Executor {

        public static final Executor INSTANCE = new PerThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class DefaultCustomEventContext implements CustomEventContext {

        private final String eventBusName;

        private final CustomSubscriber subscriber;

        private final Object event;

        private DefaultCustomEventContext(String eventBusName, CustomSubscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber != null ? subscriber.getSubscriberObject() : null;
        }

        @Override
        public Method getSubscribe() {
            return subscriber != null ? subscriber.getSubscriberMethod() : null;
        }

        @Override
        public Object getEvent() {
            return event;
        }
    }
}

