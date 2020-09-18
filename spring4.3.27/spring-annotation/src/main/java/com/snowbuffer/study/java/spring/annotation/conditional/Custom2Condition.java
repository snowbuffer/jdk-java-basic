package com.snowbuffer.study.java.spring.annotation.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-22 00:26
 */
public class Custom2Condition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 默认不匹配
        return false;
    }
}
