package com.study.book.iterator.example6;

import java.util.Iterator;

/**
 * �ۺ϶���Ľӿڣ����崴����Ӧ����������Ľӿ�
 */
public abstract class Aggregate {
    /**
     * ����������������Ӧ����������Ľӿ�
     *
     * @return ��Ӧ����������Ľӿ�
     */
    public abstract Iterator createIterator();
}
