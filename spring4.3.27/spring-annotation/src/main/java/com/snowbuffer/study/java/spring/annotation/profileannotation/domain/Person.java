package com.snowbuffer.study.java.spring.annotation.profileannotation.domain;

import lombok.Data;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 18:59
 */
@Data
public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }
}
