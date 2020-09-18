package com.snowbuffer.study.java.spring.annotation.aware;

import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 15:59
 */
public class PersonAwareConfigTest {

    @Test
    public void test() {
        BeanDefinitionPrintUtil.print(PersonAwareConfig.class);
    }
}