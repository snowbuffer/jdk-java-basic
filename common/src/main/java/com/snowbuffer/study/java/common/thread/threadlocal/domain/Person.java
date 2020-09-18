package com.snowbuffer.study.java.common.thread.threadlocal.domain;

import lombok.Data;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 19:41
 */
@Data
public class Person implements Cloneable {

    private Integer age;

    @Override
    public Person clone() {
        Person person = new Person();
        person.setAge(age);
        return person;
    }
}
