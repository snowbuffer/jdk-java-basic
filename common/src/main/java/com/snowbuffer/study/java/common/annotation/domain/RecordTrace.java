package com.snowbuffer.study.java.common.annotation.domain;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-08-11 23:38
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(RecordTraces.class)
public @interface RecordTrace {

    String value();

    String other() default "aa";
}
