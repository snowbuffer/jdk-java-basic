package com.snowbuffer.study.java.spring.annotation.beanannotation.domain;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:51
 */
public class Person3 implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("person3.doInit execute");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("person3.doDestroy execute");
    }

}
