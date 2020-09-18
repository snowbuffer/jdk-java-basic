package com.snowbuffer.study.java.spring.annotation.factorybean;

import org.springframework.context.annotation.Bean;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 10:45
 */
public class FactoryBeanConfig {

    @Bean
    public static PersonFactoryBean pp() {
        return new PersonFactoryBean();
    }
}
