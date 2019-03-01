package com.wangwenjun.concurrent.chapter4.observer.my;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 11:15
 */
public class SubTwoMyObserver extends MyObserver {

    public SubTwoMyObserver(MySubject mySubject) {
        super(mySubject);
    }

    @Override
    protected void update(Object object) {
        System.out.println("SubTwoMyObserver => " + object.toString());
    }
}

