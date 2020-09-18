package com.snowbuffer.study.java.spring.annotation.beanannotation;

import com.snowbuffer.study.java.spring.annotation.beanannotation.domain.Person3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:52
 */
@Configuration
public class BeanAnnotationConfig3 {

    @Bean(value = "person3")
    public Person3 person3() {
        return new Person3();
    }
}
