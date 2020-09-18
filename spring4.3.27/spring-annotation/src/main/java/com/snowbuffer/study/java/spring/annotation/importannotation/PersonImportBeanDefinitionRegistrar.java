package com.snowbuffer.study.java.spring.annotation.importannotation;

import com.snowbuffer.study.java.spring.annotation.importannotation.domain.Person;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 10:23
 */
public class PersonImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println(importingClassMetadata.getClassName()); // 被@Configuration标注的ImportAnnotationConfig3
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Person.class);
        registry.registerBeanDefinition("dyncPerson", beanDefinitionBuilder.getBeanDefinition());
    }
}
