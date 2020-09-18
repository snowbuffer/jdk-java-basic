package com.snowbuffer.study.java.spring.annotation.configuration;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-21 19:48
 */
public class ConfigurationConfigTest {

    @Test
    public void testPerson() {
        BeanDefinitionPrintUtil.print(ConfigurationConfig.class);
    }

}