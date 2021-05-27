package com.snowbuffer.study.java.common.generic.outer;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterizedTypeTest<T extends Number & Serializable, V> {
    /*
            实例1：Map<String, List<T>> map2
                其中Map<String, List<T>> 是 ParameterizedType
                其中String 是Class
                其中List<T> 是 新的 ParameterizedType
                其中T 是 TypeVariable

            实例2：Class<?> clz
                其中Class<?> 是 ParameterizedType
                其中? 是 WildcardType

            实例3：Class<T[]> tclzArray
                其中Class<T[]> 是 ParameterizedType
                其中T[] 是 GenericArrayType

            实例4：T key
                其中T 是 TypeVariable

            实例5：V[] values
                其中V 是 GenericArrayType

            实例6：List aList;
                其中List 是 Class
     */
    private Map<String, ParameterizedTypeTest> map;  // 存在泛型，则整个变量就是一个ParameterizedType
    private Set<String> set1;
    private Class<?> clz;
    private Class<T> tclz;
    private Class<List<T>> tclzList;
    private Class<T[]> tclzArray;
    private Class<String[]> stringclzArray;
    private Holder<String> holder;
    private List<String> list;
    private String str; // 非ParameterizedType
    private Integer i;
    private Set set;
    private List aList;
    private Map.Entry<String, String> entry;
    private List<? extends Number> a;
    private List<? super String> b;
    private T key;
    private V value;
    private V[] values;
    private List<T> tList;
    private Map<String, List<T>> map2;

    static class Holder<V> {
    }

    public static void testParameterizedType() {
        Field f = null;
        try {
            Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                if (f.getName().equals("log")) {
                    continue;
                }
                System.out.println("[" + f.getName() + "]: \n\n\t\t 不带泛型信息的ClassType: " + f.getType());
                printGenericInfo(f.getGenericType(), "\n\t\t");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printGenericInfo(Type targetType, String blank) {
        if (targetType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) targetType;
            System.out.println(blank + " genericType:" + parameterizedType.getClass());
            System.out.println(blank + " genericTypeString:" + parameterizedType);
            System.out.println(blank + " ActualTypeArguments:[");
            for (Type type : parameterizedType.getActualTypeArguments()) {
                System.out.println(blank + "\t\t ActualTypeArgumentsType:" + judgeType(type));
                if (!(type instanceof Class)) {
                    printGenericInfo(type, blank + "\t\t\t\t");
                }
            }
            System.out.println(blank + " ]");
            if (parameterizedType.getOwnerType() != null) {
                System.out.println(blank + " ownerType:" + judgeType(parameterizedType.getOwnerType()));
            } else {
                System.out.println(blank + " ownerType is null");
            }
            if (parameterizedType.getRawType() != null) {
                System.out.println(blank + " rawType:" + judgeType(parameterizedType.getRawType()));
            }
        } else if (targetType instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) targetType;
            System.out.println(blank + " typeVariableGenericDeclaration:" + typeVariable.getGenericDeclaration());
            System.out.println(blank + " typeVariableName:" + typeVariable.getName());
            if (typeVariable.getBounds().length == 0) {
                System.out.println(blank + "\t\t typeVariableBounds: 不存在");
            } else {
                for (Type type : typeVariable.getBounds()) {
                    System.out.println(blank + "\t\t typeVariableBoundName:" + type.getTypeName());
                    System.out.println(blank + "\t\t typeVariableBoundType:" + judgeType(type));
                }
            }
            if (typeVariable.getAnnotatedBounds().length == 0) {
                System.out.println(blank + "\t\t typeVariableAnnotatedBounds: 不存在");
            } else {
                for (AnnotatedType annotatedBound : typeVariable.getAnnotatedBounds()) {
                    System.out.println(blank + "\t\t typeVariableAnnotatedBound:" + annotatedBound);
                    System.out.println(blank + "\t\t typeVariableAnnotatedBoundType:" + annotatedBound.getType().getTypeName());
                }
            }
        } else if (targetType instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) targetType;
            if (wildcardType.getUpperBounds().length == 0) {
                System.out.println(blank + "\t\t wildcardTypeUpperBoundType: 不存在");
            } else {
                for (Type type : wildcardType.getUpperBounds()) {
                    System.out.println(blank + "\t\t wildcardTypeUpperBoundType:" + judgeType(type));
                }
            }
            if (wildcardType.getLowerBounds().length == 0) {
                System.out.println(blank + "\t\t wildcardTypeLowerBoundType: 不存在");
            } else {
                for (Type type : wildcardType.getLowerBounds()) {
                    System.out.println(blank + "\t\t wildcardTypeLowerBoundType:" + judgeType(type));
                }
            }
        } else if (targetType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) targetType;
            System.out.println(blank + "\t\t genericArrayTypeGenericComponentType:" + judgeType(genericArrayType.getGenericComponentType()));
            if (!(genericArrayType.getGenericComponentType() instanceof Class)) {
                printGenericInfo(genericArrayType.getGenericComponentType(), blank + "\t\t\t\t");
            }
        } else if (targetType instanceof Class) {
            System.out.println(blank + " class:" + targetType);
        }

    }

    private static String judgeType(Type type) {
        if (type == null) {
            return "是一个Null" + "[" + type + "]";
        }
        if (type instanceof Class) {
            return "是一个Class" + "[" + type + "]";
        }
        if (type instanceof ParameterizedType) {
            return "是一个ParameterizedType" + "[" + type + "]";
        }
        if (type instanceof TypeVariable) {
            return "是一个TypeVariable" + "[" + type + "]";
        }
        if (type instanceof WildcardType) {
            return "是一个WildcardType" + "[" + type + "]";
        }
        if (type instanceof GenericArrayType) {
            return "是一个GenericArrayType" + "[" + type + "]";
        }
        return "未知" + "[" + type + "]";
    }

    public static void main(String[] args) {
        testParameterizedType();
    }

}