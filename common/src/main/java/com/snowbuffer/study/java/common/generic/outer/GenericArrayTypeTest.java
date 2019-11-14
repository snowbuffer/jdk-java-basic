package com.snowbuffer.study.java.common.generic.outer;

import java.lang.reflect.*;
import java.util.List;

public class GenericArrayTypeTest<T> {

    /**
     * 含有泛型数组的才是GenericArrayType
     *
     * @param pTypeArray GenericArrayType type :java.util.List<java.lang.String>[];
     *                   genericComponentType:java.util.List<java.lang.String>
     * @param vTypeArray GenericArrayType type :T[];genericComponentType:T
     * @param list       ParameterizedType type :java.util.List<java.lang.String>;
     * @param strings    type :class [Ljava.lang.String;
     * @param test       type :class [Lcom.wangji.demo.GenericArrayTypeTest;
     */
    public void testGenericArrayType(List<String>[] pTypeArray, T[] vTypeArray
            , List<String> list, String[] strings, GenericArrayTypeTest[] test) {
    }

    /**
     * 1、getGenericComponentType
     * 返回泛型数组中元素的Type类型，即List<String>[] 中的 List<String>（ParameterizedTypeImpl）
     * 、T[] 中的T（TypeVariableImpl）；
     * 值得注意的是，无论是几维数组，getGenericComponentType()方法都只会脱去最右边的[]，返回剩下的值；
     */
    public static void testGenericArrayType() {
        Method[] declaredMethods = GenericArrayTypeTest.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().startsWith("main")) {
                continue;
            }
            System.out.println("declare Method:" + method);
            /**
             * 获取当前参数所有的类型信息
             */
            Type[] types = method.getGenericParameterTypes();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    System.out.println("ParameterizedType type :" + type);
                } else if (type instanceof GenericArrayType) {
                    System.out.println("GenericArrayType type :" + type);
                    Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
                    /**
                     * 获取泛型数组中元素的类型，要注意的是：无论从左向右有几个[]并列，
                     * 这个方法仅仅脱去最右边的[]之后剩下的内容就作为这个方法的返回值。
                     */
                    System.out.println("genericComponentType:" + genericComponentType);
                } else if (type instanceof WildcardType) {
                    System.out.println("WildcardType type :" + type);
                } else if (type instanceof TypeVariable) {
                    System.out.println("TypeVariable type :" + type);
                } else {
                    System.out.println("type :" + type);
                }
            }
        }
    }

    public static void main(String[] args) {
        testGenericArrayType();
    }
}