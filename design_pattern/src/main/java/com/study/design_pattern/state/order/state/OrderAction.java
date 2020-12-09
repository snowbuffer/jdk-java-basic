package com.study.design_pattern.state.order.state;

public enum OrderAction {


    //    预占用库存(Status: INVISIBLE=>WAITING_PAY)
    DO_PRE_OCCUPY_STORE("预占用库存", OrderStatusEnum.WAITING_PAY),
    //    创建订单失败(Status: INVISIBLE=>CREATE_ORDER_ERROR)
    DO_CREATE_ERROR("创建订单失败", OrderStatusEnum.CREATE_ORDER_ERROR),

    //预占用库存(Status: INVISIBLE=>UNDER_REVIEW)
    //支付OK(Status: WAITING_PAY=>WAITING_SEND_PROD)
    DO_PAY("完成支付", OrderStatusEnum.WAITING_SEND_PROD),
    //支付超时后恢复库存(Status: WAITING_PAY=>PAY_TIMEOUT_CANCELLED)
    DO_PAY_TIMEOUT_CANCEL_STORE_RELEASE("支付超时检查后系统取消订单", OrderStatusEnum.PAY_TIMEOUT_CANCELLED),
    //恢复手工取消未支付订单的库存(Status: WAITING_PAY=>MANUAL_CANCELLED)
    DO_PAY_MANUAL_CANCEL_STORE_RELEASE("手工取消未支付订单后系统取消订单", OrderStatusEnum.MANUAL_CANCELLED),

    //发货超时取消(Status: WAITING_SEND_PROD=>SEND_TIMEOUT_CANCEL_WAITINGREFUND)
    DO_SEND_PROD_TIMEOUT_CANCEL_WAITINGREFUND("发货超时取消", OrderStatusEnum.SEND_TIMEOUT_CANCEL_WAITINGREFUND),
    //订单退款成功，结束订单(Status: SEND_TIMEOUT_CANCEL_WAITINGREFUND=>SEND_TIMEOUT_CANCELLED)
    DO_SEND_PROD_TIMEOUT_CANCEL("超时未发货，订单关闭", OrderStatusEnum.SEND_TIMEOUT_CANCELLED),

    //    //发货(Status: WAITING_SEND_PROD=>PROD_SENDED)
    DO_PROD_SEND("发货", OrderStatusEnum.PROD_SENDED),
    //超时完成订单(Status: PROD_SENDED=>CONFIRM_TIMEOUT_FINISHED)
    DO_SENDED_ORDER_AUTO_COMPLETE("超时完成订单", OrderStatusEnum.CONFIRM_TIMEOUT_FINISHED),

    //手工完成订单(Status: PROD_SENDED=>CONFIRM_FINISHED)
    DO_SENDED_ORDER_MANUAL_COMPLETE("手工完成订单", OrderStatusEnum.CONFIRM_FINISHED);


    OrderAction(String name, OrderStatusEnum status) {
        this.name = name;
        this.status = status;
    }

    private String name;

    private OrderStatusEnum status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
}
