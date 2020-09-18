package com.snowbuffer.study.java.spring.annotation.importannotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 10:19
 */
public class PersonImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.snowbuffer.study.java.spring.annotation.importannotation.domain.Person"};
    }
}
