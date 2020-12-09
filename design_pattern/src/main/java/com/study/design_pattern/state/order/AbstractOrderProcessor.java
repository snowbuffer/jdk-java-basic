package com.study.design_pattern.state.order;

import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;

/**
 * Description:  订单处理器
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
     * 处理订单
     *
     * @param orderId          订单id
     * @param targetStatusEnum 迁移至目标状态
     * @param orderContext     订单上下文
     * @return 处理结果
     */
    protected abstract ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext);
}
