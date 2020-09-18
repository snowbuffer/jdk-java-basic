package com.snowbuffer.study.java.spring.annotation.conditional;

import com.snowbuffer.study.java.spring.annotation.conditional.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 00:23
 */
@Configuration
public class ConditionalConfig1 {

    @Conditional(value = Custom1Condition.class) // 当前bean#name == person1 匹配
    @Bean(value = "person1")
    public Person person1() {
        return new Person("person1");
    }

    @Conditional(value = Custom2Condition.class)  // 不匹配
    @Bean(value = "person2")
    public Person person2() {
        return new Person("person2");
    }
}
