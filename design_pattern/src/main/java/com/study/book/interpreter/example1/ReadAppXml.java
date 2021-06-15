package com.study.book.interpreter.example1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * ��ȡ�����ļ�
 */
public class ReadAppXml {
    /**
     * ��ȡ�����ļ�����
     *
     * @param filePathName �����ļ���·�����ļ���
     * @throws Exception
     */
    public void read(String filePathName) throws Exception {
        Document doc = null;
        //����һ������������
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //���һ��DocumentBuilder���������������˾����DOM������
        DocumentBuilder builder = factory.newDocumentBuilder();
        //�õ�һ����ʾXML�ĵ���Document����
        doc = builder.parse(filePathName);
        //ȥ��XML�ĵ�����Ϊ��ʽ�����ݵĿհ׶�ӳ����DOM���еĲ���Ҫ��Text Node����
        doc.normalize();

//		//��ȡjdbc
//		NodeList jdbc = doc.getElementsByTagName("jdbc");
//		//ֻ��һ��jdbc,��ȡjdbc�е������������
//		NodeList driverClassNode = ((Element)jdbc.item(0)).getElementsByTagName("driver-class");
//		String driverClass = driverClassNode.item(0).getFirstChild().getNodeValue();
//		System.out.println("driverClass=="+driverClass);
//		//ͬ���ȡurl��user��password��ֵ
//		NodeList urlNode = ((Element)jdbc.item(0)).getElementsByTagName("url");
//		String url = urlNode.item(0).getFirstChild().getNodeValue();
//		System.out.println("url=="+url);
//		
//		NodeList userNode = ((Element)jdbc.item(0)).getElementsByTagName("user");
//		String user = userNode.item(0).getFirstChild().getNodeValue();
//		System.out.println("user=="+user);
//		
//		NodeList passwordNode = ((Element)jdbc.item(0)).getElementsByTagName("password");
//		String password = passwordNode.item(0).getFirstChild().getNodeValue();
//		System.out.println("password=="+password);
//		//��ȡapplication-xml
//		NodeList applicationXmlNode = doc.getElementsByTagName("application-xml");
//		String applicationXml = applicationXmlNode.item(0).getFirstChild().getNodeValue();
//		System.out.println("applicationXml=="+applicationXml);

        //��Ҫ��ȡspring-default��Ȼ���ȡapplication-xmls
        //Ȼ����ܻ�ȡapplication-xml
        NodeList springDefaultNode = doc.getElementsByTagName("spring-default");
        NodeList appXmlsNode = ((Element) springDefaultNode.item(0)).getElementsByTagName("application-xmls");
        NodeList appXmlNode = ((Element) appXmlsNode.item(0)).getElementsByTagName("application-xml");
        //ѭ����ȡÿ��application-xmlԪ�ص�ֵ
        for (int i = 0; i < appXmlNode.getLength(); i++) {
            String applicationXml = appXmlNode.item(i).getFirstChild().getNodeValue();
            System.out.println("applicationXml==" + applicationXml);
        }
    }

    public static void main(String[] args) throws Exception {
        ReadAppXml t = new ReadAppXml();
        t.read("book/App2.xml");
    }
}
