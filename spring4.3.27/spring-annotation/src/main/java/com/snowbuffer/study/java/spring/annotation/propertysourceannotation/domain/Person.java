package com.snowbuffer.study.java.spring.annotation.propertysourceannotation.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 17:31
 */
@Data
public class Person {
    @Value("${person.name:张三}")
    private String name;

}
