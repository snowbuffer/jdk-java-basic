package com.study.design_pattern.bridge;


/**
 * 桥接模式： 其实就是把多个实体类中具有共性的部分剥离(抽象)出来
 * 例如：
 * 抽象的答题卡（创建答题卡，发布答题卡，撤销答题卡）
 * 实体：课后答题卡，随堂答题卡
 * 再例如：发送消息
 * 抽象的消息：（发送消息，统计消息）
 * 实体： 短信消息，邮箱消息，公众号消息
 */
public abstract class Phone {
	
	//组合品牌
	private Brand brand;

	//构造器
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
