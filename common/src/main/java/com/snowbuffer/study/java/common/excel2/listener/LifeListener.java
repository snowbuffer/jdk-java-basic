package com.snowbuffer.study.java.common.excel2.listener;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 14:10
 */
public interface LifeListener<T> {

    void onEvent(ObservableRunnable.RunnableEvent<T> event);
}
