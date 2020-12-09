package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderProcessor;
import com.study.design_pattern.state.order.OrderContext;
import com.study.design_pattern.state.order.ProcessResult;
import com.study.design_pattern.state.order.annotation.OrderProcessor;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

// 超时未支付，手工取消订单
@Component
@OrderProcessor
public class ManualCancelledProcessor extends AbstractOrderProcessor {

    public ManualCancelledProcessor() {
        setOrderAction(OrderAction.DO_PAY_MANUAL_CANCEL_STORE_RELEASE);
    }

    @Override
    public ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext) {
        return ProcessResult.ofBizCheckSuccessResult();
    }
}
