package com.snowbuffer.study.java.spring.annotation.configuration;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-09-03 14:51
 */
public class TestConfig12Test {

    @Test
    public void test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig1.class, TestConfig2.class);
        System.out.println(applicationContext);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    static class A {

    }
}