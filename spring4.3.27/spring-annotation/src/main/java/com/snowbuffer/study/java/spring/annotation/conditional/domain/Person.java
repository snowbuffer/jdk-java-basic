package com.snowbuffer.study.java.spring.annotation.conditional.domain;

import lombok.Data;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 00:22
 */
@Data
public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }
}
