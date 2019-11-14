package com.snowbuffer.study.java.common.generic.outer;

import java.lang.reflect.*;
import java.util.List;

public class WildcardTypeTest {
    /**
     * 1、 a: 获取ParameterizedType:? extends java.lang.Number
     * 2、上界：class java.lang.Number
     */
    private List<? extends Number> a;

    /**
     * b: 获取ParameterizedType:? super java.lang.String
     * 上届：class java.lang.Object
     * 下届：class java.lang.String
     */
    private List<? super String> b;

    /**
     * c: 获取ParameterizedType:class java.lang.String
     */
    private List<String> c;

    /**
     * aClass: 获取ParameterizedType:?
     * 上届：class java.lang.Object
     */
    private Class<?> aClass;

    private String wangji;

    /**
     * 多种数据进行混合
     */
    public static void testWildcardType() {
        Field f = null;
        try {
            Field[] fields = WildcardTypeTest.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                if (f.getName().equals("log")) {
                    continue;
                }
                System.out.println("begin ******当前field:" + f.getName() + " *************************");
                if (f.getGenericType() instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();
                    for (Type type : parameterizedType.getActualTypeArguments()) {
                        System.out.println(f.getName() + ": 获取ParameterizedType:" + type);
                        if (type instanceof WildcardType) {
                            printWildcardType((WildcardType) type);
                        }
                    }
                } else if (f.getGenericType() instanceof GenericArrayType) {
                    GenericArrayType genericArrayType = (GenericArrayType) f.getGenericType();
                    System.out.println("GenericArrayType type :" + genericArrayType);
                    Type genericComponentType = genericArrayType.getGenericComponentType();
                    if (genericComponentType instanceof WildcardType) {
                        printWildcardType((WildcardType) genericComponentType);
                    }
                } else if (f.getGenericType() instanceof TypeVariable) {
                    TypeVariable typeVariable = (TypeVariable) f.getGenericType();
                    System.out.println("typeVariable:" + typeVariable);

                } else {
                    System.out.println("type :" + f.getGenericType());
                    if (f.getGenericType() instanceof WildcardType) {
                        printWildcardType((WildcardType) f.getGenericType());
                    }
                }
                System.out.println("end ******当前field:" + f.getName() + " *************************");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void printWildcardType(WildcardType wildcardType) {
        for (Type type : wildcardType.getUpperBounds()) {
            System.out.println("上界：" + type);
        }
        for (Type type : wildcardType.getLowerBounds()) {
            System.out.println("下界：" + type);
        }
    }

    public static void main(String[] args) {
        testWildcardType();
    }
}











