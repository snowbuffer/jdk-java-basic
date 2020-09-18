package com.snowbuffer.study.java.springmvc.annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-23 00:39
 */
@Configuration
@ComponentScan(value = "com.snowbuffer.study.java.springmvc.annotation",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})})
public class AppConfig {
}
