package com.wangwenjun.concurrent.chapter4.observer.my;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 11:14
 */
public class SubOneMyObserver extends MyObserver {

    public SubOneMyObserver(MySubject mySubject) {
        super(mySubject);
    }

    @Override
    public void update(Object object) {
        System.out.println("SubOneMyObserver => " + object.toString());
    }
}

