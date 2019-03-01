package com.wangwenjun.concurrency.custombus;

import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-02-18 16:05
 */
public class CustomSubscriber {

    private Object subscriberObject;

    private Method subscriberMethod;

    private boolean disabled;

    public CustomSubscriber(Object subscriberObject, Method subscriberMethod) {
        this.subscriberObject = subscriberObject;
        this.subscriberMethod = subscriberMethod;
        this.disabled = false;
    }

    public Object getSubscriberObject() {
        return subscriberObject;
    }

    public void setSubscriberObject(Object subscriberObject) {
        this.subscriberObject = subscriberObject;
    }

    public Method getSubscriberMethod() {
        return subscriberMethod;
    }

    public void setSubscriberMethod(Method subscriberMethod) {
        this.subscriberMethod = subscriberMethod;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}

