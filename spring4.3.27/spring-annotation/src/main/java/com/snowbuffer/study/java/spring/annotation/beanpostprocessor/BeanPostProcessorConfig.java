package com.snowbuffer.study.java.spring.annotation.beanpostprocessor;

import com.snowbuffer.study.java.spring.annotation.beanpostprocessor.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 16:20
 */
@Configuration
public class BeanPostProcessorConfig {

    @Bean
    public static PersonBeanPostProcessor personBeanPostProcessor() {
        return new PersonBeanPostProcessor();
    }

    @Bean
    public Person person() {
        return new Person("aaa");
    }
}
