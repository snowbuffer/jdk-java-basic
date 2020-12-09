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
     * 状态流转方法
     *
     * @param orderId 订单id
     * @param action  流转的订单操作事件
     * @param status  当前订单状态(todo:://应该从订单数据库查询获取)
     * @return 扭转后的订单状态
     */
    public OrderStatusEnum handleOrder(final Long orderId, OrderAction action, final OrderStatusEnum status, OrderContext orderContext) {
        if (this.isFinalStatus(status)) {
            throw new IllegalArgumentException("handle action can't process final state order.");
        }
        //todo://order Id --query DB and get currentStatus
        // 获取对应处理器,根据入参状态和时间获取订单流转的结果状态
        AbstractOrderStateOperator abstractOrderOperator = this.getStateOperator(action);
        StatusResult resState = abstractOrderOperator.stateTransform(status);
        // 得到结果状态，在对应的processor中处理订单数据及其相关信息
        if (resState.equals(OrderStatusEnum.ERROR)) {
            throw new IllegalStateException(String.format("订单状态流转失败，订单id:%s", resState.getErrMsg()));
        }
        AbstractOrderProcessor orderProcessor = this.getOrderProcessor(action);
        doTrans(orderId, orderProcessor, resState, orderContext);
        return resState.getOrderStatus();
    }

    //todo::@Transactional
    private void doTrans(final Long orderId, AbstractOrderProcessor orderProcessor, StatusResult resState, OrderContext orderContext) {
        String errInfoTeamplate = "订单状态流转失败，订单id:%s,原因:%s";
        try {
            ProcessResult processResult = orderProcessor.process(orderId, resState.getOrderStatus(), orderContext);
            if (processResult == null || !processResult.isSuccess()) {
                throw new IllegalStateException(String.format(errInfoTeamplate, orderId, processResult.getFailedReason()));
            } else {
                //todo:: update Order table record => restState,只有真正改变时，再更新数据库
            }

        } catch (Throwable ex) {
            throw new IllegalStateException(String.format(errInfoTeamplate, orderId, ex.getMessage()));
        }
    }

    /**
     * 根据入参状态枚举实例获取对应的状态处理器
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
     * 根据入参状态枚举实例获取对应的状态后处理器
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
     * 判断是不是已完成或者已取消订单订单
     */
    private boolean isFinalStatus(OrderStatusEnum status) {
        return OrderStatusEnum.CONFIRM_FINISHED == status
                || OrderStatusEnum.PAY_TIMEOUT_CANCELLED == status || OrderStatusEnum.MANUAL_CANCELLED == status ||
                OrderStatusEnum.SEND_TIMEOUT_CANCELLED == status || OrderStatusEnum.CONFIRM_TIMEOUT_FINISHED == status;
    }
}
