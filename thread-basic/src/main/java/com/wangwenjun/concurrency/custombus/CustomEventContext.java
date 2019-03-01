package com.wangwenjun.concurrency.custombus;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 16:33
 */
public interface CustomEventContext {

    String getSource();

    Object getSubscriber();

    Method getSubscribe();

    Object getEvent();
}
