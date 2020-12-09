package com.study.design_pattern.state.order;

import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-12-09 20:51
 */
public class OrderStateManager {

    public static Map<OrderAction, AbstractOrderStateOperator> orderOperatorMaps = new HashMap<>();

    public static Map<OrderAction, AbstractOrderProcessor> orderProcessorMaps = new HashMap<OrderAction, AbstractOrderProcessor>();

    public OrderStateManager() {
    }

    /**
     * ״̬��ת����
     *
     * @param orderId ����id
     * @param action  ��ת�Ķ��������¼�
     * @param status  ��ǰ����״̬(todo:://Ӧ�ôӶ������ݿ��ѯ��ȡ)
     * @return Ťת��Ķ���״̬
     */
    public OrderStatusEnum handleOrder(final Long orderId, OrderAction action, final OrderStatusEnum status, OrderContext orderContext) {
        if (this.isFinalStatus(status)) {
            throw new IllegalArgumentException("handle action can't process final state order.");
        }
        //todo://order Id --query DB and get currentStatus
        // ��ȡ��Ӧ������,�������״̬��ʱ���ȡ������ת�Ľ��״̬
        AbstractOrderStateOperator abstractOrderOperator = this.getStateOperator(action);
        StatusResult resState = abstractOrderOperator.stateTransform(status);
        // �õ����״̬���ڶ�Ӧ��processor�д��������ݼ��������Ϣ
        if (resState.equals(OrderStatusEnum.ERROR)) {
            throw new IllegalStateException(String.format("����״̬��תʧ�ܣ�����id:%s", resState.getErrMsg()));
        }
        AbstractOrderProcessor orderProcessor = this.getOrderProcessor(action);
        doTrans(orderId, orderProcessor, resState, orderContext);
        return resState.getOrderStatus();
    }

    //todo::@Transactional
    private void doTrans(final Long orderId, AbstractOrderProcessor orderProcessor, StatusResult resState, OrderContext orderContext) {
        String errInfoTeamplate = "����״̬��תʧ�ܣ�����id:%s,ԭ��:%s";
        try {
            ProcessResult processResult = orderProcessor.process(orderId, resState.getOrderStatus(), orderContext);
            if (processResult == null || !processResult.isSuccess()) {
                throw new IllegalStateException(String.format(errInfoTeamplate, orderId, processResult.getFailedReason()));
            } else {
                //todo:: update Order table record => restState,ֻ�������ı�ʱ���ٸ������ݿ�
            }

        } catch (Throwable ex) {
            throw new IllegalStateException(String.format(errInfoTeamplate, orderId, ex.getMessage()));
        }
    }

    /**
     * �������״̬ö��ʵ����ȡ��Ӧ��״̬������
     */
    private AbstractOrderStateOperator getStateOperator(OrderAction action) {
        AbstractOrderStateOperator operator = null;
        for (Map.Entry<OrderAction, AbstractOrderStateOperator> entry : orderOperatorMaps.entrySet()) {
            if (action == entry.getKey()) {
                operator = entry.getValue();
            }
        }
        if (null == operator) {
            throw new IllegalArgumentException(String.format("can't find proper operator. The parameter state :%s", action.toString()));
        }
        return operator;
    }

    /**
     * �������״̬ö��ʵ����ȡ��Ӧ��״̬������
     */
    private AbstractOrderProcessor getOrderProcessor(OrderAction action) {
        AbstractOrderProcessor processor = null;
        for (Map.Entry<OrderAction, AbstractOrderProcessor> entry : orderProcessorMaps.entrySet()) {
            if (action == entry.getKey()) {
                processor = entry.getValue();
            }
        }
        if (null == processor) {
            throw new IllegalArgumentException(String.format("can't find proper processor. The parameter state :%s", action.toString()));
        }
        return processor;
    }


    /**
     * �ж��ǲ�������ɻ�����ȡ����������
     */
    private boolean isFinalStatus(OrderStatusEnum status) {
        return OrderStatusEnum.CONFIRM_FINISHED == status
                || OrderStatusEnum.PAY_TIMEOUT_CANCELLED == status || OrderStatusEnum.MANUAL_CANCELLED == status ||
                OrderStatusEnum.SEND_TIMEOUT_CANCELLED == status || OrderStatusEnum.CONFIRM_TIMEOUT_FINISHED == status;
    }
}
