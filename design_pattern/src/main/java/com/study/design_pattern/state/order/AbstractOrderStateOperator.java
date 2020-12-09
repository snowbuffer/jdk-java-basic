package com.study.design_pattern.state.order;

import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;

/**
 * Description:  ����״̬��
 *
 * @author cjb
 * @since 2020-12-09 20:13
 */
public abstract class AbstractOrderStateOperator {

    private OrderAction action;

    public void setAction(OrderAction action) {
        this.action = action;
    }

    public OrderAction getAction() {
        return action;
    }

    /**
     * ����״̬
     *
     * @param lastOrderStatus ��һ��״̬
     * @return ������
     */
    protected abstract StatusResult stateTransform(OrderStatusEnum lastOrderStatus);
}
