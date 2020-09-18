package com.snowbuffer.study.java.spring.annotation.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 00:26
 */
public class Custom1Condition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean find = false;
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes("org.springframework.context.annotation.Bean");
        Object name = annotationAttributes.get("name");
        if (name.getClass().isArray()) {
            Object[] nameArray = (Object[]) name;
            for (Object item : nameArray) {
                if (item.equals("person1")) {
                    find = true;
                    break;
                }
            }
        }
        return find;
    }
}
