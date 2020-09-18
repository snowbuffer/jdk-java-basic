package com.snowbuffer.study.java.spring.annotation.beanannotation;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 13:54
 */
public class BeanAnnotationConfig2Test {

    @Test
    public void test() {
        BeanDefinitionPrintUtil.print(BeanAnnotationConfig2.class);
        AnnotationConfigApplicationContext applicationContext = (AnnotationConfigApplicationContext) BeanDefinitionPrintUtil.getApplicationContext();
        applicationContext.close();
    }
}