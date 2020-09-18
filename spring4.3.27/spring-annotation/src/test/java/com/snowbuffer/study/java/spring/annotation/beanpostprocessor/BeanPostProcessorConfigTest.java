package com.snowbuffer.study.java.spring.annotation.beanpostprocessor;

import com.snowbuffer.study.java.spring.annotation.beanpostprocessor.domain.Person;
import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 16:21
 */
public class BeanPostProcessorConfigTest {

    @Test
    public void test() {
        BeanDefinitionPrintUtil.print(BeanPostProcessorConfig.class);
        Person bean = BeanDefinitionPrintUtil.getApplicationContext().getBean(Person.class);
        System.out.println(bean);

        AnnotationConfigApplicationContext applicationContext = (AnnotationConfigApplicationContext) BeanDefinitionPrintUtil.getApplicationContext();
        applicationContext.close();
    }
}