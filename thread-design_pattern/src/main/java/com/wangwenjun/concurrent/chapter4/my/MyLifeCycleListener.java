package com.wangwenjun.concurrent.chapter4.my;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 11:42
 */
public interface MyLifeCycleListener {

    void publishEvent(MyObservableRunnable.ThreadEvent threadEvent);
}
