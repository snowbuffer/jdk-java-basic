package com.study.book.facade.example3;

/**
 * ʾ���������ݲ��ģ��
 */
public class DAO {
    public void generate() {
        //1�������ù��������ȡ��Ӧ��������Ϣ
        ConfigModel cm = ConfigManager.getInstance().getConfigData();
        if (cm.isNeedGenDAO()) {
            //2������Ҫ��ȥ������Ӧ�Ĵ��룬��������ļ�
            System.out.println("�����������ݲ�����ļ�");
        }
    }
}