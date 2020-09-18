package com.snowbuffer.study.java.spring.annotation.factorybean;

import com.snowbuffer.study.java.spring.annotation.factorybean.domain.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 10:46
 */
public class PersonFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
