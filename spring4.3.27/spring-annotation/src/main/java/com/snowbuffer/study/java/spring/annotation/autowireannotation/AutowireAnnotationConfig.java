package com.snowbuffer.study.java.spring.annotation.autowireannotation;

import com.snowbuffer.study.java.spring.annotation.autowireannotation.domain.Person;
import com.snowbuffer.study.java.spring.annotation.autowireannotation.domain.PersonWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 17:51
 */
@Configuration
public class AutowireAnnotationConfig {

    @Bean
    public Person person1() {
        return new Person("person1");
    }

    @Bean
    public Person person2() {
        return new Person("person3");
    }

    @Bean
    public Person person3() {
        return new Person("person2");
    }

    @Bean
    public PersonWrapper personWrapper() {
        return new PersonWrapper();
    }
}
