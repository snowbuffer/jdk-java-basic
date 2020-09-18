package com.snowbuffer.study.java.spring.annotation.aware;

import com.snowbuffer.study.java.spring.annotation.aware.domain.PersonAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 15:57
 */
@Configuration
public class PersonAwareConfig {

    @Bean
    public PersonAware personAware() {
        return new PersonAware();
    }
}
