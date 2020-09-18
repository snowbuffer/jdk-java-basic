package com.snowbuffer.study.java.spring.annotation.configuration;

import com.snowbuffer.study.java.spring.annotation.configuration.domain.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author cjb
 * @Configuration注解原理： 1. 将当前MainConfig注册成 一个BeanDefintion
 * 2. ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry 拦截beanFactory中所有的beanNames
 * 并检查每个beanName，是否被标注了@Configuration,如果有，那么开始解析@Configuration流程
 * 1. 优先解析：MainConfig中的被标注@Configuration的成员类
 * 2. 解析MainConfig中标注的@PropertySources
 * -> todo
 * 3. 解析MainConfig中标注的@ComponentScans
 * -> 注册ComponentScans下的beanNames
 * -> 检查每个beanName, 是否被标注了@Configuration,如果有，那么开始解析@Configuration流程
 * 4. 解析MainConfig中标注@Import   indirectly present模式
 * -> 先导入先关bean定义
 * -> 检查每个beanName, 是否被标注了@Import,如果有，那么开始解析@Import流程
 * 5. 解析MainConfig中标注@ImportResource
 * 6. 解析MainConfig中标注@Bean  indirectly present模式
 * 7. 解析MainConfig#superClass Note: 无论父类是否是抽象类，都会被解析
 * -> 以superClass开始，重新进行解析@Configuration流程
 * 8. @Bean方法创建实例逻辑： org.springframework.context.annotation.ConfigurationClassEnhancer.BeanMethodInterceptor
 * <p>
 * 特别注意： ConfigurationConfig上标不标注@Configuration都可以
 * 标注了 @Configuration 表示是全模式，ConfigurationConfig会被cglib增强（内部的@Bean返回的实例不会增强，但是方法内部调用ConfigurationConfig会被拦截）
 * 如果没有标注 @Configuration 表示是精简模式， ConfigurationConfig不会被cglib增强
 * <p>
 * 不管有没有标注 @Configuration , 不影响 ConfigurationConfig上或者内部的注解配置
 * 详见：org.springframework.context.annotation.ConfigurationClassPostProcessor#processConfigBeanDefinitions(org.springframework.beans.factory.support.BeanDefinitionRegistry)
 * => else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
 * configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
 * }
 * <p>
 * cglib增强功能：
 * 详见：org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 * => enhanceConfigurationClasses(beanFactory);
 * =>  if (ConfigurationClassUtils.isFullConfigurationClass(beanDef)) {
 * ...
 * }
 * @since 2020-07-21 19:47
 */
@Configuration
public class ConfigurationConfig extends SuperConfigurationConfig {

    @Bean
    public Person personInstance() {
        Person person = new Person();
        person.setAge(1);
        person.setName("aa");
        return person;
    }


}
