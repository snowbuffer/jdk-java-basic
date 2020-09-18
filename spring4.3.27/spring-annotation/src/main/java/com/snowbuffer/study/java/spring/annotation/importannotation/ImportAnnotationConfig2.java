package com.snowbuffer.study.java.spring.annotation.importannotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 09:56
 */
@Configuration
@Import(value = {PersonImportSelector.class}) // 此种方式，通过判断来决定是否需要导入某个类
public class ImportAnnotationConfig2 {
}
