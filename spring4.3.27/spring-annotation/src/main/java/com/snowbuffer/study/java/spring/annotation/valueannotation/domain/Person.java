package com.snowbuffer.study.java.spring.annotation.valueannotation.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 16:57
 */
@Data
public class Person {

    // @Value 解析原理：org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement
    @Value("${name:张三}")  // env
    private String name;

    @Value("#{10 * 200}")  // spel表达式
    private Integer age;

    @Value("${user.dir}") // env
    private String userHome;
}
