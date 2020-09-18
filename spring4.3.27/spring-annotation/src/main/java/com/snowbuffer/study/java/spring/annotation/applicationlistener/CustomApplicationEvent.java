package com.snowbuffer.study.java.spring.annotation.applicationlistener;

import org.springframework.context.ApplicationEvent;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 23:16
 */
public class CustomApplicationEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CustomApplicationEvent(Object source) {
        super(source);
    }
}
