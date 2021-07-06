package com.study.book.interpreter.example3;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Ԫ����Ϊ���ս����Ӧ�Ľ����������Ͳ�ִ���м�Ԫ��
 */
public class ElementExpression extends ReadXmlExpression {
    /**
     * ������¼��ϵ�ReadXmlExpressionԪ��  ��Ҫ��˳�����, ���磺 /root/a/b/c
     */
    private Collection<ReadXmlExpression> eles = new ArrayList<ReadXmlExpression>();
    /**
     * Ԫ�ص�����
     */
    private String eleName = "";

    public ElementExpression(String eleName) {
        this.eleName = eleName;
    }

    public boolean addEle(ReadXmlExpression ele) {
        this.eles.add(ele);
        return true;
    }

    public boolean removeEle(ReadXmlExpression ele) {
        this.eles.remove(ele);
        return true;
    }

    public String[] interpret(Context c) {
        //��ȡ����������ĵ�ǰԪ����Ϊ����Ԫ��
        //���ҵ���ǰԪ����������Ӧ��xmlԪ�أ������ûص���������
        Element pEle = c.getPreEle();
        if (pEle == null) {
            //˵�����ڻ�ȡ���Ǹ�Ԫ��
            c.setPreEle(c.getDocument().getDocumentElement());
        } else {
            //���ݸ���Ԫ�غ�Ҫ���ҵ�Ԫ�ص���������ȡ��ǰ��Ԫ��
            Element nowEle = c.getNowEle(pEle, eleName);
            //�ѵ�ǰ��ȡ��Ԫ�طŵ�����������
            c.setPreEle(nowEle);
        }

        //ѭ��������Ԫ�ص�interpret����
        String[] ss = null;
        for (ReadXmlExpression ele : eles) {
            ss = ele.interpret(c);
        }
        return ss;
    }
}
