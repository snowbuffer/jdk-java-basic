package com.wangwenjun.concurrency.custombus;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 15:32
 */
public interface CustomBus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object event, String topic);

    void close();

    String getBusName();
}
