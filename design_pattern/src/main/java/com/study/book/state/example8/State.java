package com.study.book.state.example8;

/**
 * ����״̬�ӿ�
 */
public interface State {
    /**
     * ִ��״̬��Ӧ�Ĺ��ܴ���
     *
     * @param ctx �����ĵ�ʵ������
     */
    public void doWork(StateMachine ctx);
}
