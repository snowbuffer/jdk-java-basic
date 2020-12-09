package com.study.design_pattern.state.order.processor;

import com.study.design_pattern.state.order.AbstractOrderProcessor;
import com.study.design_pattern.state.order.OrderContext;
import com.study.design_pattern.state.order.ProcessResult;
import com.study.design_pattern.state.order.annotation.OrderProcessor;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
@OrderProcessor
public class WaitingPayProcessor extends AbstractOrderProcessor {

    public WaitingPayProcessor() {
        setOrderAction(OrderAction.DO_PRE_OCCUPY_STORE);
    }

    @Override
    protected ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext) {
        System.out.println("WaitingPayProcessor");
        return ProcessResult.ofBizCheckSuccessResult();
    }
}
