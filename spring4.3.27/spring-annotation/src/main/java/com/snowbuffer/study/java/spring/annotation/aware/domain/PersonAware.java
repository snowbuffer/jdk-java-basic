package com.snowbuffer.study.java.spring.annotation.aware.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 15:53
 */
public class PersonAware implements BeanNameAware, BeanClassLoaderAware,
        BeanFactoryAware, EnvironmentAware, EmbeddedValueResolverAware, ResourceLoaderAware, ApplicationEventPublisherAware,
        MessageSourceAware, ApplicationContextAware {
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("classLoader:" + classLoader.getClass());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("beanFactory:" + beanFactory.getClass());
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("beanName:" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext:" + applicationContext.getClass());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("applicationEventPublisher:" + applicationEventPublisher.getClass());
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println("resolver:" + resolver.getClass());
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("environment:" + environment.getClass());
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        System.out.println("messageSource:" + messageSource.getClass());
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        System.out.println("resourceLoader:" + resourceLoader.getClass());
    }
}
