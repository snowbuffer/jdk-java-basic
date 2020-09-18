package com.snowbuffer.study.java.spring.annotation.aop;

import com.snowbuffer.study.java.spring.annotation.aop.aspect.SystemAspect;
import com.snowbuffer.study.java.spring.annotation.aop.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 19:27
 */
@Configuration
@EnableAspectJAutoProxy
/**
 * 先找到
 */
public class AopConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public SystemAspect systemAspect() {
        return new SystemAspect();
    }
}
