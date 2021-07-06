package com.study.book.interpreter.example4;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlUtil {
    public static Document getRoot(String filePathName) throws Exception {
        filePathName = XmlUtil.class.getClassLoader().getResource(filePathName).getPath();
        Document doc = null;
        //����һ������������
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //���һ��DocumentBuilder���������������˾����DOM������
        DocumentBuilder builder = factory.newDocumentBuilder();
        //�õ�һ����ʾXML�ĵ���Document����
        doc = builder.parse(filePathName);
        //ȥ��XML�ĵ�����Ϊ��ʽ�����ݵĿհ׶�ӳ����DOM���еĲ���Ҫ��Text Node����
        doc.normalize();
        return doc;
    }
}
