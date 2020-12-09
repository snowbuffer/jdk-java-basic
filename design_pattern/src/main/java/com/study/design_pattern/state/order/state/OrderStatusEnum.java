package com.study.design_pattern.state.order.state;

public enum OrderStatusEnum {
    //不可见
    INVISIBLE("不可见"),

    CREATE_ORDER_ERROR("创建订单失败"),

    WAITING_PAY("待支付"),
    WAITING_SEND_PROD("待发货"),
    PAY_TIMEOUT_CANCELLED("支付超时-订单自动关闭"),
    MANUAL_CANCELLED("用户未支付-手工取消订单"),
    PROD_SENDED("已发货"),
    SEND_TIMEOUT_CANCEL_WAITINGREFUND("发货超时-等待退款"),
    SEND_TIMEOUT_CANCELLED("发货超时-系统自动关闭订单"),
    CONFIRM_TIMEOUT_FINISHED("确认收货-系统超时确认完成"),
    CONFIRM_FINISHED("确认收货-买家手工确认完成"),
    ERROR("错误订单");


    OrderStatusEnum(String desc) {
        this.desc = desc;
    }

    public static OrderStatusEnum getByStatusStr(String orderStatus) {
        OrderStatusEnum[] orderStatusEnums = OrderStatusEnum.values();
        for (int i = 0; i < orderStatusEnums.length; i++) {
            if (orderStatusEnums[i].name().equals(orderStatus)) {
                return orderStatusEnums[i];
            }
        }
        return null;
    }

    public String getDesc() {
        return this.desc;
    }

    private String desc;
}
