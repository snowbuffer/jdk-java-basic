package com.wangwenjun.concurrent.chapter4.observer.my;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-03-01 11:11
 */
public class MySubject {

    private List<MyObserver> observers = new LinkedList<>();

    public void attach(MyObserver myObserver) {
        if (!observers.contains(myObserver)) {
            observers.add(myObserver);
        }
    }

    public void publish(Object content) {
        observers.stream().forEach((item) -> {
            item.update(content);
        });
    }

    public static void main(String[] args) {
        MySubject mySubject = new MySubject();
        new SubOneMyObserver(mySubject);
        new SubTwoMyObserver(mySubject);

        mySubject.publish("哈哈哈");
    }
}

