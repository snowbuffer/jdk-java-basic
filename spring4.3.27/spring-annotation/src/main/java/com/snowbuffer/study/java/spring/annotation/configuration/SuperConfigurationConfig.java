package com.snowbuffer.study.java.spring.annotation.configuration;

import com.snowbuffer.study.java.spring.annotation.configuration.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-31 10:47
 */
@Configuration
@EnableAsync
@PropertySource(name = "personProperties",
        value = {"classpath:/com/snowbuffer/study/java/spring/annotation/propertysourceannotation/domain/personProperties.properties"})
@Import(value = {com.snowbuffer.study.java.spring.annotation.importannotation.domain.Person.class})
public abstract class SuperConfigurationConfig {

    @Bean
    public Person personInstance2() {
        Person person = new Person();
        person.setAge(1);
        person.setName("aa");
        return person;
    }
}
