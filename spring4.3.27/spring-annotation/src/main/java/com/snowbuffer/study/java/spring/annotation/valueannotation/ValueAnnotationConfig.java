package com.snowbuffer.study.java.spring.annotation.valueannotation;

import com.snowbuffer.study.java.spring.annotation.valueannotation.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 16:58
 */
@Configuration
public class ValueAnnotationConfig {

    @Bean
    public Person person() {
        return new Person();
    }

}
