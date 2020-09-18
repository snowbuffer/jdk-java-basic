package com.snowbuffer.study.java.spring.annotation.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 10:36
 */
public class BeanDefinitionPrintUtil {

    private static ApplicationContext globalApplicationContext;

    public static <T> void print(Class<T> configClass) {
        print(configClass, true);
    }

    public static <T> void print(Class<T> configClass, boolean excludeSpringFrameworkContextXXProcessor) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(configClass);
        globalApplicationContext = applicationContext;
        print(applicationContext, excludeSpringFrameworkContextXXProcessor);
    }

    public static void print(ApplicationContext applicationContext, boolean excludeSpringFrameworkContextXXProcessor) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(beanName -> {
            if (excludeSpringFrameworkContextXXProcessor && beanName.startsWith("org.springframework.context")) {
                return;
            }
            System.out.println("beanName:" + beanName + "; instance:" + applicationContext.getBean(beanName));
        });
    }

    public static ApplicationContext getApplicationContext() {
        return globalApplicationContext;
    }
}
