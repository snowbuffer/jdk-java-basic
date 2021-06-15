package com.snowbuffer.study.java.common.excel2.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-11 09:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ExcelColumn {


    String columnName();

    int index();

    int width() default -1;

}
