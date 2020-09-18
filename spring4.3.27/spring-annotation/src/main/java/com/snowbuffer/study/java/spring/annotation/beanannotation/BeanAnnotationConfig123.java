package com.snowbuffer.study.java.spring.annotation.beanannotation;

import com.snowbuffer.study.java.spring.annotation.beanannotation.domain.Person123;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:52
 */
@Configuration
public class BeanAnnotationConfig123 {

    @Bean(value = "person123", initMethod = "doInit", destroyMethod = "doDestroy")
    public Person123 person123() {
        return new Person123();
    }
}
