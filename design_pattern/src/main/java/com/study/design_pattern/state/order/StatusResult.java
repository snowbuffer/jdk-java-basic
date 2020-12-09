package com.study.design_pattern.state.order;


import com.study.design_pattern.state.order.state.OrderStatusEnum;

/**
 * 需要放在二方包发布
 */
public class StatusResult {
    private OrderStatusEnum orderStatus;
    private String errMsg;

    private StatusResult(OrderStatusEnum orderStatus, String errMsg) {
        this.orderStatus = orderStatus;
        this.errMsg = errMsg;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static StatusResult OfFailedResult(String errMsg) {
        return new StatusResult(OrderStatusEnum.ERROR, errMsg);
    }

    public static StatusResult OfSuccessResult(OrderStatusEnum orderStatus) {
        return new StatusResult(orderStatus, null);
    }
}
