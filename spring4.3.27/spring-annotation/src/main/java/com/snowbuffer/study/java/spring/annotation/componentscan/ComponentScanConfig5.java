package com.snowbuffer.study.java.spring.annotation.componentscan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-21 23:12
 */
@Configuration
@ComponentScan(value = { // 如果value 没有设置，那么默认以标注了@ComponentScan所在的类的包作为基础扫描包
        "com.snowbuffer.study.java.spring.annotation.componentscan.controller",
        "com.snowbuffer.study.java.spring.annotation.componentscan.service",
},
        includeFilters = {@Filter(type = FilterType.CUSTOM, classes = {ComponentScanConfig5.MyCustomFilter.class})},
        useDefaultFilters = false)
@RestController
public class ComponentScanConfig5 {


    public static class MyCustomFilter implements TypeFilter {

        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

            doOther(metadataReaderFactory);

            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
            Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
            return annotationTypes.contains("org.springframework.stereotype.Controller");
        }

        private void doOther(MetadataReaderFactory metadataReaderFactory) {

            System.out.println("doOther start");
            try {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader("com.snowbuffer.study.java.spring.annotation.componentscan.ComponentScanConfig5");

                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                System.out.println(annotationMetadata.getClassName());
                // 读取的是(RestController注解上所有的注解信息，包括继承)：[org.springframework.stereotype.Controller, org.springframework.stereotype.Component, org.springframework.web.bind.annotation.ResponseBody]
                System.out.println(annotationMetadata.getMetaAnnotationTypes("org.springframework.web.bind.annotation.RestController"));
                System.out.println(annotationMetadata.hasAnnotation("org.springframework.stereotype.Component"));
                System.out.println(annotationMetadata.isAnnotated("org.springframework.stereotype.Component"));
                Set<String> annotationTypesSet = annotationMetadata.getAnnotationTypes();
                annotationTypesSet.stream().forEach(annotation -> {
                    System.out.println(annotation);
                    if (annotation.equals("org.springframework.context.annotation.ComponentScan")) {
                        System.out.println(annotationMetadata.isAnnotated("org.springframework.context.annotation.ComponentScan"));
                        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes("org.springframework.context.annotation.ComponentScan");
                        System.out.println(annotationAttributes);
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("doOther end");
        }
    }
}
