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
@Import(value = {PersonImportBeanDefinitionRegistrar.class})
// 此种方式： 动态为当前ImportAnnotationConfig3添加类似于@Bean功能的BeanDefintino
public class ImportAnnotationConfig3 {
}
