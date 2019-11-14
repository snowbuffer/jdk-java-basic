package com.snowbuffer.study.java.common.generic;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @version V1.0
 * @since 2019-11-14 17:47
 */
public class GenericTest<T extends Number & Serializable> {

    private Map<String, List<T>> map; // 无论有无变量 只要存在<>就表示ParameterizedType的一个实例

    private String str;  // 不存在任何的<>, 表示一个Class的实例

    private Map<String, List<T>> map1;  // 单个T表示TypeVariable的一个实例

    private Map<String, List<?>> map2; // 单个?表示WildcardType的一个实例

    private Map<String, List<? extends T>> map3;

    public static void main(String[] args) {
        test("str");
        System.out.println("----------------------------");
        test("map");
        System.out.println("----------------------------");
        test("map1");
        System.out.println("----------------------------");
        test("map2");
        System.out.println("----------------------------");
        test("map3");
    }

    private static void testMapField(Field map) {
        Type genericType = map.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType impl = (ParameterizedType) genericType;
            Type[] actualTypeArguments = impl.getActualTypeArguments();
            for (Type argument : actualTypeArguments) {
                if (argument instanceof Class) {
                    System.out.println("String is Class: " + argument.getClass());
                }
                if (argument instanceof ParameterizedType) {
                    System.out.println("List<T> is ParameterizedType: " + argument.getClass());
                    ParameterizedType argumentPT = (ParameterizedType) argument;
                    System.out.println("List<T> ownerType is null: " + argumentPT.getOwnerType());
                    System.out.println("List<T> rawType is java.util.List: " + argumentPT.getRawType());
                    Type[] actualTypeArguments1 = argumentPT.getActualTypeArguments();
                    for (Type t : actualTypeArguments1) {

                        // map1
                        if (t instanceof TypeVariable) {
                            System.out.println("List<T> is TypeVariable: " + t.getClass());
                            TypeVariable ttv = (TypeVariable) t;
                            System.out.println("List<T> which T is source from GenericTest: " + ttv.getGenericDeclaration());
                            Type[] bounds = ttv.getBounds();
                            for (Type bound : bounds) {
                                System.out.println("List<T> T bound is: " + bound.getClass() + "; name: " + bound.getTypeName());
                            }
                        }

                        // map2/map3
                        if (t instanceof WildcardType) {
                            System.out.println("List<?> is WildcardType: " + t.getClass());
                            WildcardType twt = (WildcardType) t;
                            Type[] lowerBounds = twt.getLowerBounds();
                            for (Type lowerBound : lowerBounds) {
                                System.out.println("List<?> ? lowerBound is: " + lowerBound.getClass() + "; name: " + lowerBound.getTypeName());
                            }
                            Type[] upperBounds = twt.getUpperBounds();
                            for (Type upperBound : upperBounds) {
                                System.out.println("List<?> ? upperBound is: " + upperBound.getClass() + "; name: " + upperBound.getTypeName());
                            }
                        }
                    }
                }
            }
        }
    }

    private static void testMapFieldGenericType(Field map) {
        Type genericType = map.getGenericType();
        System.out.println(map.getName() + " genericType类型 =>" + genericType.getClass());
    }

    private static void testStrFieldGenericType(Field str) {
        Type genericType = str.getGenericType();
        System.out.println(str.getName() + " genericType类型 =>" + genericType.getClass());
    }

    public static void test(String field) {
        Field f = null;
        try {
            Field[] fields = GenericTest.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                if (f.getName().equals(field)) {
                    if (field.equals("str")) {
                        testStrFieldGenericType(f);
                    }
                    if (field.equals("map")) {
                        testMapFieldGenericType(f);
                    }
                    if (field.equals("map1")) {
                        testMapField(f);
                    }
                    if (field.equals("map2")) {
                        testMapField(f);
                    }
                    if (field.equals("map3")) {
                        testMapField(f);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

