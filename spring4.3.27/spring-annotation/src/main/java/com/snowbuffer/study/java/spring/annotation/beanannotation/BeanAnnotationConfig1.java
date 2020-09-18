package com.snowbuffer.study.java.spring.annotation.beanannotation;

import com.snowbuffer.study.java.spring.annotation.beanannotation.domain.Person1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:52
 */
@Configuration
public class BeanAnnotationConfig1 {

    @Bean(value = "person1", initMethod = "doInit", destroyMethod = "doDestroy")
    public Person1 person1() {
        return new Person1();
    }
}
