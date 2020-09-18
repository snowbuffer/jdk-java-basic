package com.snowbuffer.study.java.spring.annotation.componentscan;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-21 23:08
 */
public class ComponentScanConfig1Test {

    @Test
    public void test1() {
        BeanDefinitionPrintUtil.print(ComponentScanConfig1.class);
    }
}