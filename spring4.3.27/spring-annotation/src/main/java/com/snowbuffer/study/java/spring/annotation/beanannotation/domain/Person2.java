package com.snowbuffer.study.java.spring.annotation.beanannotation.domain;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:51
 */
public class Person2 {

    @PostConstruct
    public void doInit() {
        System.out.println("person2.doInit execute");
    }

    @PreDestroy
    public void doDestroy() {
        System.out.println("person2.doDestroy execute");
    }
}
