package com.study.book.state.example7;

/**
 * ������˽�������
 */
public class AuditOverState implements LeaveRequestState {
    public void doWork(StateMachine request) {
        //�Ȱ�ҵ��������ͻ���
        LeaveRequestModel lrm = (LeaveRequestModel) request.getBusinessVO();

        //ҵ���������������¼�������̽���
    }
}
