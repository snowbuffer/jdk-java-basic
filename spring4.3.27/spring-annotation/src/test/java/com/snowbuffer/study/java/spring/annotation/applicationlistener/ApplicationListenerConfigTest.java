package com.snowbuffer.study.java.spring.annotation.applicationlistener;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 22:40
 */
public class ApplicationListenerConfigTest {

    @Test
    public void test() {
        BeanDefinitionPrintUtil.print(ApplicationListenerConfig.class);
        AnnotationConfigApplicationContext applicationContext = (AnnotationConfigApplicationContext) BeanDefinitionPrintUtil.getApplicationContext();
        applicationContext.publishEvent(new CustomApplicationEvent("自定义事件"));
        applicationContext.close();
    }
}