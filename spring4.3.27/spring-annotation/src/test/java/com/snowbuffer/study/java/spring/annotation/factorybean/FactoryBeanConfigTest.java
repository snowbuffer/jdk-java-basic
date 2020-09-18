package com.snowbuffer.study.java.spring.annotation.factorybean;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;


/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 10:47
 */
public class FactoryBeanConfigTest {

    @Test
    public void test() {
        BeanDefinitionPrintUtil.print(FactoryBeanConfig.class);
        ApplicationContext applicationContext = BeanDefinitionPrintUtil.getApplicationContext();
        System.out.println(applicationContext.getBean("pp") == applicationContext.getBean("pp"));
        System.out.println(applicationContext.getBean("&pp").getClass());
    }
}