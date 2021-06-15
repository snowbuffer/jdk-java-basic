package com.study.book.interpreter.example5;

import org.w3c.dom.Element;

import java.util.List;

/**
 * �Զ��Ԫ�ص�������Ϊ�ս���Ľ��ʹ������
 */
public class PropertysTerminalExpression extends ReadXmlExpression {
    /**
     * ��������
     */
    private String propName;

    public PropertysTerminalExpression(String propName) {
        this.propName = propName;
    }

    public String[] interpret(Context c) {
        //��ȡ���Ķ��Ԫ��
        List<Element> eles = c.getPreEles();

        String[] ss = new String[eles.size()];
        //ѭ�����Ԫ�أ���ȡÿ�������Ե�ֵ
        for (int i = 0; i < ss.length; i++) {
            ss[i] = eles.get(i).getAttribute(this.propName);
        }
        return ss;
    }
}
