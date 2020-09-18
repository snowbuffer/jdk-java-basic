package com.snowbuffer.study.java.spring.annotation.autowireannotation.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 17:52
 */
@Data
public class PersonWrapper {

    @Qualifier("person1")
    @Autowired
    private Person person;

}
