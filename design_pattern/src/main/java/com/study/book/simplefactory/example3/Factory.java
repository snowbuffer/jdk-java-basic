package com.study.book.simplefactory.example3;

/**
 * �����࣬��������Api����
 */
public class Factory {
    /**
     * ����Ĵ���Api����ķ���
     *
     * @return ����õ�Api����
     */
    public static Api createApi() {
        //����ֻ��һ��ʵ�֣��Ͳ��������ж���
        return new Impl();
    }
}