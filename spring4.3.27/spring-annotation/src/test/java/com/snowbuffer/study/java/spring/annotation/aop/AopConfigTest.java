package com.snowbuffer.study.java.spring.annotation.aop;

import com.snowbuffer.study.java.spring.annotation.aop.service.UserService;
import com.snowbuffer.study.java.spring.annotation.common.BeanDefinitionPrintUtil;
import org.junit.Test;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 19:30
 */
public class AopConfigTest {

    @Test
    public void test() {
        BeanDefinitionPrintUtil.print(AopConfig.class);

        UserService userService = BeanDefinitionPrintUtil.getApplicationContext().getBean(UserService.class);

        userService.test();


    }
}