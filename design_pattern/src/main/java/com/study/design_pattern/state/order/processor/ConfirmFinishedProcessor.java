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
public class ConfirmFinishedProcessor extends AbstractOrderProcessor {

    public ConfirmFinishedProcessor() {
        setOrderAction(OrderAction.DO_SENDED_ORDER_MANUAL_COMPLETE);
    }

    @Override
    public ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext) {

        return ProcessResult.ofBizCheckSuccessResult();
    }
}
