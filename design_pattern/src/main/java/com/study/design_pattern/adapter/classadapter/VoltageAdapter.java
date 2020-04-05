package com.study.design_pattern.adapter.classadapter;

//�������� => �����Լ�ȥʵ���µĽӿ�
public class VoltageAdapter extends Voltage220V implements IVoltage5V {

	@Override
	public int output5V() {
		// TODO Auto-generated method stub
		//��ȡ��220V��ѹ
		int srcV = output220V();
		int dstV = srcV / 44 ; //ת�� 5v
		return dstV;
	}

}
