package com.study.design_pattern.state.order;

import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;

/**
 * Description:  订单状态机
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
     * 处理状态
     *
     * @param lastOrderStatus 上一个状态
     * @return 处理结果
     */
    protected abstract StatusResult stateTransform(OrderStatusEnum lastOrderStatus);
}
