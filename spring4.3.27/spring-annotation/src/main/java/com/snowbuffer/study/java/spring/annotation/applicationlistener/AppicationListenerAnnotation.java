package com.snowbuffer.study.java.spring.annotation.applicationlistener;

import org.springframework.context.event.EventListener;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 23:33
 */
public class AppicationListenerAnnotation {

    @EventListener(value = {CustomApplicationEvent.class})
    public void event(CustomApplicationEvent event) {
        System.out.println("AppicationListenerAnnotation:event =>" + event);
    }
}
