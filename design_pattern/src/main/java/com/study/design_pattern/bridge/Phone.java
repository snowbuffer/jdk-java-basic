package com.study.design_pattern.bridge;


/**
 * �Ž�ģʽ�� ��ʵ���ǰѶ��ʵ�����о��й��ԵĲ��ְ���(����)����
 * ���磺
 * ����Ĵ��⿨���������⿨���������⿨���������⿨��
 * ʵ�壺�κ���⿨�����ô��⿨
 * �����磺������Ϣ
 * �������Ϣ����������Ϣ��ͳ����Ϣ��
 * ʵ�壺 ������Ϣ��������Ϣ�����ں���Ϣ
 */
public abstract class Phone {
	
	//���Ʒ��
	private Brand brand;

	//������
	public Phone(Brand brand) {
		super();
		this.brand = brand;
	}
	
	protected void open() {
		this.brand.open();
	}
	protected void close() {
		brand.close();
	}
	protected void call() {
		brand.call();
	}
	
}
