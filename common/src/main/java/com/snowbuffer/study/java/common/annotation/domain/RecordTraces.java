package com.snowbuffer.study.java.common.annotation.domain;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 23:40
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RecordTraces {

    RecordTrace[] value() default {};
}
