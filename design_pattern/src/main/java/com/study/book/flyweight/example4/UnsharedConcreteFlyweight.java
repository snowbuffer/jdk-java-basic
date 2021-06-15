package com.study.book.flyweight.example4;

import java.util.ArrayList;
import java.util.List;

/**
 * ����Ҫ��������Ԫ�����ʵ�֣�Ҳ�����ģʽ�е���϶���
 */
public class UnsharedConcreteFlyweight implements Flyweight {
    /**
     * ��¼ÿ����϶����������������
     */
    private List<Flyweight> list = new ArrayList<Flyweight>();

    public void add(Flyweight f) {
        list.add(f);
    }

    public boolean match(String securityEntity, String permit) {
        for (Flyweight f : list) {
            //�ݹ����
            if (f.match(securityEntity, permit)) {
                return true;
            }
        }
        return false;
    }
}