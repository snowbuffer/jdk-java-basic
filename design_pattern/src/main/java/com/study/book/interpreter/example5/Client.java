package com.study.book.interpreter.example5;

public class Client {
    public static void main(String[] args) throws Exception {
        //׼��������
        Context c = new Context("book/InterpreterTest.xml");
        //ͨ����������ȡ�����﷨��
        ReadXmlExpression re = Parser.parse("root/a/b/d$.id$");
        //�����������ȡ����ֵ
        String ss[] = re.interpret(c);
        for (String s : ss) {
            System.out.println("d������idֵ��=" + s);
        }

        //���Ҫʹ��ͬһ�������ģ��������н�������Ҫ���³�ʼ�������Ķ���
        c.reInit();
        ReadXmlExpression re2 = Parser.parse("root/a/b/d$");
        //�����������ȡ����ֵ
        String ss2[] = re2.interpret(c);
        for (String s : ss2) {
            System.out.println("d��ֵ��=" + s);
        }
    }
}