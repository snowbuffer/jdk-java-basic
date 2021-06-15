package com.study.book.decorator.example5;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class Client {
    public static void main(String[] args) throws Exception {
        //��ʽ����ļ�
        DataOutputStream dout = new DataOutputStream(
                new EncryptOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream("book/MyEncrypt.txt"))));
        //Ȼ��Ϳ������������
        dout.write("abcdxyz".getBytes());
        dout.close();
    }
}
