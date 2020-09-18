package com.snowbuffer.study.java.spring.annotation.componentscan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-21 23:08
 */
@Configuration
@ComponentScan(value = {
        "com.snowbuffer.study.java.spring.annotation.componentscan.controller",
        "com.snowbuffer.study.java.spring.annotation.componentscan.service"
})
public class ComponentScanConfig1 {
}
