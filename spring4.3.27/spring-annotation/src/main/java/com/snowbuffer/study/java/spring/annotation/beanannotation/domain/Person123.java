package com.snowbuffer.study.java.spring.annotation.beanannotation.domain;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:51
 */
public class Person123 implements InitializingBean, DisposableBean {

    public void doInit() {
        System.out.println("@bean init method person123.doInit execute");
    }

    public void doDestroy() {
        System.out.println("@bean destroy method person123.doDestroy execute");
    }

    @PostConstruct
    public void doInit123() {
        System.out.println("@PostConstruct person123.doInit execute");
    }

    @PreDestroy
    public void doDestroy123() {
        System.out.println("@PostConstruct person123.doDestroy execute");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean person123.doInit execute");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean person123.doDestroy execute");
    }

}
