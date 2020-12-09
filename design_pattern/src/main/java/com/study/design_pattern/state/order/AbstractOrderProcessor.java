package com.study.design_pattern.state.order;

import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;

/**
 * Description:  ����������
 *
 * @author cjb
 * @since 2020-12-09 20:10
 */
public abstract class AbstractOrderProcessor {

    private OrderAction orderAction;

    public void setOrderAction(OrderAction orderAction) {
        this.orderAction = orderAction;
    }

    public OrderAction getOrderAction() {
        return orderAction;
    }

    /**
     * ������
     *
     * @param orderId          ����id
     * @param targetStatusEnum Ǩ����Ŀ��״̬
     * @param orderContext     ����������
     * @return ������
     */
    protected abstract ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext);
}
