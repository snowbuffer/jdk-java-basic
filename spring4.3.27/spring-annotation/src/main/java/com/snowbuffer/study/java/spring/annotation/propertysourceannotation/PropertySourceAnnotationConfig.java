package com.snowbuffer.study.java.spring.annotation.propertysourceannotation;

import com.snowbuffer.study.java.spring.annotation.propertysourceannotation.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 17:32
 */
@Configuration
@PropertySource(name = "personProperties",
        value = {"classpath:/com/snowbuffer/study/java/spring/annotation/propertysourceannotation/domain/personProperties.properties"})
public class PropertySourceAnnotationConfig {

    @Bean
    public Person person() {
        return new Person();
    }
}
