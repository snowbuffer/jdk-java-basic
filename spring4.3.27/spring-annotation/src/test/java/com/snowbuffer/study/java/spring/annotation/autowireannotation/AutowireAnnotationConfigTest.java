package com.snowbuffer.study.java.spring.annotation.autowireannotation;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 17:53
 */
public class AutowireAnnotationConfigTest {

    @Test
    public void test() {
        BeanDefinitionPrintUtil.print(AutowireAnnotationConfig.class);

    }
}