package com.snowbuffer.study.java.common.annotation.domain;

import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * <pre>
 *  总结：
 *
 *      directly present: {
 *          直接注解在ManageUser1上
 *      }
 *
 *      indirectly present: {
 *          RecordTraces中获取RecordTrace
 *      }
 *
 *      present: {
 *          直接注解在ManageUser1上
 *          直接注解在BaseUser1上，但是注解必须是@Inherited
 *      }
 *
 *      associated: {
 *          直接注解在ManageUser1上
 *          直接注解在BaseUser1上，但是注解必须是@Inherited
 *          RecordTraces中获取RecordTrace
 *      }
 * </pre>
 * <br/>
 * <table border>
 * <tr><th colspan=2>Method</th><th>[Directly Present直接注解]</th><th>[Indirectly Presentrepeate注解]</th><th>[Present父类注解]</th><th>[Associated所有注解]</th>
 * <tr><td align=right>{@code T}</td><td>{@link #getAnnotation(Class) getAnnotation(Class&lt;T&gt;)}
 * <td align=center>支持</td><td align=center>-</td><td align=center>支持</td><td align=center>-</td>
 * </tr>
 * <tr><td align=right>{@code Annotation[]}</td><td>{@link #getAnnotations getAnnotations()}
 * <td align=center>支持</td><td align=center>-</td><td align=center>支持</td><td align=center>-</td>
 * </tr>
 * <tr><td align=right>{@code T[]}</td><td>{@link #getAnnotationsByType(Class) getAnnotationsByType(Class&lt;T&gt;)}
 * <td align=center>支持</td><td align=center>支持</td><td align=center>支持</td><td align=center>支持</td>
 * </tr>
 * <tr><td align=right>{@code T}</td><td>{@link #getDeclaredAnnotation(Class) getDeclaredAnnotation(Class&lt;T&gt;)}
 * <td align=center>支持</td><td align=center>-</td><td></td><td align=center>-</td>
 * </tr>
 * <tr><td align=right>{@code Annotation[]}</td><td>{@link #getDeclaredAnnotations getDeclaredAnnotations()}
 * <td align=center>支持</td><td align=center>-</td><td align=center>-</td><td align=center>-</td>
 * </tr>
 * <tr><td align=right>{@code T[]}</td><td>{@link #getDeclaredAnnotationsByType(Class) getDeclaredAnnotationsByType(Class&lt;T&gt;)}
 * <td align=center>支持</td><td align=center>支持</td><td align=center>-</td><td align=center>-</td>
 * </tr>
 * </table>
 *
 * @author cjb
 * @since 2020-08-11 23:45
 */
public class ManageUser1Test {


    /**
     * present定义：
     * 1.直接注解在ManageUser1上  即：directlyPresent
     * 2.直接注解在BaseUser1上，但是注解必须是@Inherited
     */
    @Test
    public void testPresent() {
        /**
         *
         * @RecordTrace(value = "BaseUser1", other = "bb")
         * public class BaseUser1 {
         * }
         *
         *
         * @RecordTrace(value = "ManageUser1")
         * public class ManageUser1 extends BaseUser1 {
         * }
         *
         *
         * [注解支持继承]如果子类父类同时存在注解，则取子类，属性不会合并
         * [注解不支持继承]子类没有，那就真的没有，不考虑父类是否有注解
         *
         */
        AnnotatedElement element = ManageUser1.class;
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof RecordTrace) {
                RecordTrace recordTrace = (RecordTrace) annotation;
                System.out.println(annotation.annotationType().getSimpleName() + "=" + recordTrace.value() + ";" + recordTrace.other());
                // RecordTrace=ManageUser1;aa
            }
        }

        System.out.println("=====");
        RecordTrace recordTrace = element.getAnnotation(RecordTrace.class);
        System.out.println(recordTrace.annotationType().getSimpleName() + "=" + recordTrace.value() + ";" + recordTrace.other());
    }

    /**
     * indirectlyPresent定义：
     * 1. 仅从当前类上的@RecordTraces中获取@RecordTrace
     */
    @Test
    public void testIndirectlyPresent() {
        /**
         *
         * @RecordTraces({
         *         @RecordTrace(value = "BaseUser1"),
         *         @RecordTrace(value = "BaseUser11")
         * })
         * public class BaseUser1 {
         * }
         *
         * @RecordTraces({
         *         @RecordTrace(value = "ManageUser1"),
         *         @RecordTrace(value = "ManageUser11")
         * })
         * public class ManageUser1 extends BaseUser1 {
         * }
         *
         */

        AnnotatedElement element = ManageUser1.class;
        Annotation[] annotations = element.getDeclaredAnnotationsByType(RecordTrace.class);
        for (Annotation annotation : annotations) {
            if (annotation instanceof RecordTrace) {
                RecordTrace recordTrace = (RecordTrace) annotation;
                System.out.println(annotation.annotationType().getSimpleName() + "=" + recordTrace.value() + ";" + recordTrace.other());
                // RecordTrace=ManageUser1;aa
                // RecordTrace=ManageUser11;aa
            }
        }
    }

    /**
     * associated定义：
     * 1.直接注解在ManageUser1上  即：directlyPresent
     * 2.直接注解在BaseUser1上，但是注解必须是@Inherited
     * 3.@RecordTraces中获取@RecordTrace 但是注解必须是@Inherited
     */
    @Test
    public void testAssociated() {

        /**
         *
         * @RecordTraces({
         *         @RecordTrace(value = "BaseUser1"),
         *         @RecordTrace(value = "BaseUser11")
         * })
         * public class BaseUser1 {
         * }
         *
         * @RecordTraces({
         *         @RecordTrace(value = "ManageUser1"),
         *         @RecordTrace(value = "ManageUser11")
         * })
         * public class ManageUser1 extends BaseUser1 {
         * }
         *
         */
        AnnotatedElement element = ManageUser1.class;
        Annotation[] annotations = element.getAnnotationsByType(RecordTrace.class);
        for (Annotation annotation : annotations) {
            if (annotation instanceof RecordTrace) {
                RecordTrace recordTrace = (RecordTrace) annotation;
                System.out.println(annotation.annotationType().getSimpleName() + "=" + recordTrace.value() + ";" + recordTrace.other());
                // RecordTrace=ManageUser1;aa
                // RecordTrace=ManageUser11;aa
            }
        }
    }

    @Test
    public void testtemp() {
        /**
         * @RecordTraces({
         *         @RecordTrace(value = "ManageUser1"),
         *         @RecordTrace(value = "ManageUser11")
         * })
         * @RecordTrace(value = "ManageUser111")
         * public class ManageUser1 extends BaseUser1 {
         * }
         */
        MultiValueMap<String, Object> one = AnnotatedElementUtils.getAllAnnotationAttributes(
                ManageUser1.class, RecordTrace.class.getName(), false, false);  // false, false  将RecordTrace中所有的属性封装成MultiValueMap
        System.out.println(one); // {value=[ManageUser111], other=[aa]}

        System.out.println("===");

        MultiValueMap<String, Object> two = AnnotatedElementUtils.getAllAnnotationAttributes(
                ManageUser1.class, RecordTraces.class.getName(), false, false); // false, false  将RecordTrace中所有的属性封装成MultiValueMap
        for (Map.Entry<String, List<Object>> entry : two.entrySet()) {
            List<Object> valuelList = entry.getValue();
            for (Object o : valuelList) {
                if (o.getClass().isArray()) {
                    int length = Array.getLength(o);
                    for (int i = 0; i < length; i++) {
                        Object o1 = Array.get(o, i);
                        if (o1 instanceof RecordTrace) {  // 原始类型是@RecordTrace
                            RecordTrace recordTrace = (RecordTrace) o1;
                            System.out.println(entry.getKey() + " =>" + recordTrace.value());
                            // value =>ManageUser1
                            // value =>ManageUser11
                        }
                    }
                }
            }
        }

        System.out.println("===");

        MultiValueMap<String, Object> three1 = AnnotatedElementUtils.getAllAnnotationAttributes(
                ManageUser1.class, RecordTrace.class.getName(), true, true); // 如果一个注解中的任意属性不是注解形式，那么true/false效果一样
        System.out.println(three1); // {value=[ManageUser111], other=[aa]}
        three1 = AnnotatedElementUtils.getAllAnnotationAttributes(
                ManageUser1.class, RecordTrace.class.getName(), false, false); // 如果一个注解中的任意属性不是注解形式，那么true/false效果一样
        System.out.println(three1); // {value=[ManageUser111], other=[aa]}

        System.out.println("===");

        MultiValueMap<String, Object> three = AnnotatedElementUtils.getAllAnnotationAttributes(
                ManageUser1.class, RecordTraces.class.getName(), true, true); // 如果一个注解中的任意属性是注解形式，那么true表示将解析的注解属值进行二次封装：AnnotationAttributes

        for (Map.Entry<String, List<Object>> entry : three.entrySet()) {
            List<Object> valuelList = entry.getValue();
            for (Object o : valuelList) {
                if (o.getClass().isArray()) {
                    int length = Array.getLength(o);
                    for (int i = 0; i < length; i++) {
                        Object o1 = Array.get(o, i);
                        if (o1 instanceof AnnotationAttributes) { //  原始类型是@RecordTrace ,二次封装成 AnnotationAttributes
                            AnnotationAttributes annotationAttributes = (AnnotationAttributes) o1;
                            System.out.println(entry.getKey() + " =>" + annotationAttributes);
                        }
                    }
                }
            }
        }
    }
}