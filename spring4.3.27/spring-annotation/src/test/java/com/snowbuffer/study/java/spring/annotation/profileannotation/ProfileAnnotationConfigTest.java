package com.snowbuffer.study.java.spring.annotation.profileannotation;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 19:02
 */
public class ProfileAnnotationConfigTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles(new String[]{"dev", "test"});
        applicationContext.register(ProfileAnnotationConfig.class);
        applicationContext.refresh();
        BeanDefinitionPrintUtil.print(applicationContext, true);
    }
}