package com.wangwenjun.concurrent.chapter4.observer.my;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 11:10
 */
public abstract class MyObserver {

    private MySubject mySubject;

    public MyObserver(MySubject mySubject) {
        this.mySubject = mySubject;
        this.mySubject.attach(this);
    }

    protected abstract void update(Object object);
}

