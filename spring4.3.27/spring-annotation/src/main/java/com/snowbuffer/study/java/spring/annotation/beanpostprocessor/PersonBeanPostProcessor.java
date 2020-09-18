package com.snowbuffer.study.java.spring.annotation.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 16:12
 */
public class PersonBeanPostProcessor implements BeanPostProcessor, InstantiationAwareBeanPostProcessor,
        SmartInstantiationAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor, DestructionAwareBeanPostProcessor, SmartInitializingSingleton {

    boolean isPredictBeanTypePrint = false;

    boolean isRequiresDestruction = false;

    // 不常用
    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) {
        if (needPrint(beanClass) && !isPredictBeanTypePrint) { // 一个bean的生命周期中，该方法调用多次，这里为了方便查看生命周期环节，仅打印一次
            isPredictBeanTypePrint = true;
            System.out.println("1. SmartInstantiationAwareBeanPostProcessor.predictBeanType:" + beanClass + ":" + beanName);
        }
        return null;
    }

    // 创建BeanWrapper实例 时需要决定构造器
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
        if (needPrint(beanClass)) {
            System.out.println("SmartInstantiationAwareBeanPostProcessor.determineCandidateConstructors:" + beanClass + ":" + beanName);
        }
        return null;
    }

    // 不常用
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        if (needPrint(bean.getClass())) {
            System.out.println("SmartInstantiationAwareBeanPostProcessor.getEarlyBeanReference:" + bean + ":" + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (needPrint(beanClass)) {
            System.out.println("2. InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation:" + beanClass + ":" + beanName);
        }
        return null;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (needPrint(beanType)) {
            System.out.println("3. MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition:" + beanType + ":" + beanName);
        }
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (needPrint(bean.getClass())) {
            System.out.println("4. InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation:" + bean + ":" + beanName);
        }
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(
            PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        if (needPrint(bean.getClass())) {
            System.out.println("5. InstantiationAwareBeanPostProcessor.postProcessPropertyValues:" + bean + ":" + beanName);
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (needPrint(bean.getClass())) {
            System.out.println("6. BeanPostProcessor.postProcessBeforeInitialization:" + bean + ":" + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (needPrint(bean.getClass())) {
            System.out.println("7. BeanPostProcessor.postProcessAfterInitialization:" + bean + ":" + beanName);
        }
        return bean;
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        if (needPrint(bean.getClass()) && !isRequiresDestruction) {
            isRequiresDestruction = true;
            System.out.println("8. postProcessBeforeDestruction.requiresDestruction:" + bean);
        }
        return true;
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (needPrint(bean.getClass())) {
            System.out.println("9. DestructionAwareBeanPostProcessor.postProcessBeforeDestruction:" + bean + ":" + beanName);
        }
    }

    private boolean needPrint(Class<?> beanClass) {
        return "com.snowbuffer.study.java.spring.annotation.beanpostprocessor.domain.Person".equals(beanClass.getName());
    }


    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("10.PersonBeanPostProcessor.afterSingletonsInstantiated  该方法不建议放在beanpostprocessor上，建议放在业务bean上");
    }
}
