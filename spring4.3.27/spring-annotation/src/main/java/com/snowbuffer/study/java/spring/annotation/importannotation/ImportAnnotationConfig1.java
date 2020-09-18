package com.snowbuffer.study.java.spring.annotation.importannotation;

import com.snowbuffer.study.java.spring.annotation.importannotation.domain.Person;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 09:56
 */
@Configuration
@Import(value = {Person.class}) // Person需要提供一个无参构造器    此种方式：直接选择，不需要判断条件
public class ImportAnnotationConfig1 {
}
