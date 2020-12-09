package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderProcessor;
import com.study.design_pattern.state.order.OrderContext;
import com.study.design_pattern.state.order.ProcessResult;
import com.study.design_pattern.state.order.annotation.OrderProcessor;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

//支付超时取消&Try恢复库存到库存已恢复&订单已取消 或者 库存恢复失败
@Component
@OrderProcessor
public class PayTimeoutCanceledProcessor extends AbstractOrderProcessor {

    public PayTimeoutCanceledProcessor() {
        super.setOrderAction(OrderAction.DO_PAY_TIMEOUT_CANCEL_STORE_RELEASE);
    }

    public ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext) {
        return ProcessResult.ofBizCheckSuccessResult();
    }
}