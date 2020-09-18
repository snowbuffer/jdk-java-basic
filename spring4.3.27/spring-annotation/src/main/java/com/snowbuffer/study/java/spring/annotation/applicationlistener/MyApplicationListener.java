package com.snowbuffer.study.java.spring.annotation.applicationlistener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 22:39
 */
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("event: " + event);
    }
}
