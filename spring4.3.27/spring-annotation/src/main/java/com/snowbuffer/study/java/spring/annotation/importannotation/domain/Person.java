package com.snowbuffer.study.java.spring.annotation.importannotation.domain;

import lombok.Data;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 09:55
 */
@Data
public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person() {
    }
}
