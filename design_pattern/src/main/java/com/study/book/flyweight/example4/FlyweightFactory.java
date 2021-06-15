package com.study.book.flyweight.example4;

import java.util.HashMap;
import java.util.Map;

/**
 * ��Ԫ������ͨ��ʵ�ֳ�Ϊ����
 */
public class FlyweightFactory {
    private static FlyweightFactory factory = new FlyweightFactory();

    private FlyweightFactory() {

    }

    public static FlyweightFactory getInstance() {
        return factory;
    }

    /**
     * ������flyweight����
     */
    private Map<String, Flyweight> fsMap = new HashMap<String, Flyweight>();

    /**
     * ��ȡkey��Ӧ����Ԫ����
     *
     * @param key ��ȡ��Ԫ�����key
     * @return key��Ӧ����Ԫ����
     */
    public Flyweight getFlyweight(String key) {
        Flyweight f = fsMap.get(key);
        //��һ�����򵥵��д��
        if (f == null) {
            f = new AuthorizationFlyweight(key);
            fsMap.put(key, f);
        }
        return f;
    }
}