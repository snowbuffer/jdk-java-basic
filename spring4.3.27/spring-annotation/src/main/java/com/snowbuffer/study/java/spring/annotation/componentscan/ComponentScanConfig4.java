package com.snowbuffer.study.java.spring.annotation.componentscan;

import com.snowbuffer.study.java.spring.annotation.componentscan.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-21 23:12
 */
@Configuration
@ComponentScan(value = { // 如果value 没有设置，那么默认以标注了@ComponentScan所在的类的包作为基础扫描包
        "com.snowbuffer.study.java.spring.annotation.componentscan.controller",
        "com.snowbuffer.study.java.spring.annotation.componentscan.service",
},
        includeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {UserService.class})},
        useDefaultFilters = false)
public class ComponentScanConfig4 {
}
