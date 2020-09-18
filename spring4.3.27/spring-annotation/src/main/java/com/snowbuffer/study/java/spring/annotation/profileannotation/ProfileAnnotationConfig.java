package com.snowbuffer.study.java.spring.annotation.profileannotation;

import com.snowbuffer.study.java.spring.annotation.profileannotation.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 18:59
 */
@Configuration
public class ProfileAnnotationConfig {

    @Profile("dev")  // @Conditional
    @Bean
    public Person person1() {
        return new Person("devaa1");
    }

    @Profile("test")
    @Bean
    public Person person2() {
        return new Person("testaa2");
    }

    @Profile("prod")
    @Bean
    public Person person3() {
        return new Person("prodaa3");
    }
}
