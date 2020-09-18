package com.snowbuffer.study.java.spring.annotation.applicationlistener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 22:40
 */
@Configuration
public class ApplicationListenerConfig {

    @Bean
    public ApplicationListener applicationListener() {
        return new MyApplicationListener();
    }

    @Bean
    public AppicationListenerAnnotation appicationListenerAnnotation() {
        return new AppicationListenerAnnotation();
    }
}
