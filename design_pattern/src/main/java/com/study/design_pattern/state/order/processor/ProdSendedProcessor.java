package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderProcessor;
import com.study.design_pattern.state.order.OrderContext;
import com.study.design_pattern.state.order.ProcessResult;
import com.study.design_pattern.state.order.annotation.OrderProcessor;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

//快递发货, 手工发货
@Component
@OrderProcessor
public class ProdSendedProcessor extends AbstractOrderProcessor {

    public ProdSendedProcessor() {
        setOrderAction(OrderAction.DO_PROD_SEND);
    }

    public ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext) {
        return ProcessResult.ofBizCheckSuccessResult();
    }
}
