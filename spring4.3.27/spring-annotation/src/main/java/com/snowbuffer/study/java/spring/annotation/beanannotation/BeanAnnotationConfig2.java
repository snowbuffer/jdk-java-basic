package com.snowbuffer.study.java.spring.annotation.beanannotation;

import com.snowbuffer.study.java.spring.annotation.beanannotation.domain.Person2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:52
 */
@Configuration
public class BeanAnnotationConfig2 {

    @Bean(value = "person2")
    public Person2 person2() {
        return new Person2();
    }
}
