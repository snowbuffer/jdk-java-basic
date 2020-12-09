package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderProcessor;
import com.study.design_pattern.state.order.OrderContext;
import com.study.design_pattern.state.order.ProcessResult;
import com.study.design_pattern.state.order.annotation.OrderProcessor;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

//发货超时
@Component
@OrderProcessor
public class SendTimeoutCancellReviewProcessor extends AbstractOrderProcessor {
    public SendTimeoutCancellReviewProcessor() {
        setOrderAction(OrderAction.DO_SEND_PROD_TIMEOUT_CANCEL_WAITINGREFUND);
    }

    public ProcessResult process(Long orderId, OrderStatusEnum targetStatusEnum, OrderContext orderContext) {
        return ProcessResult.ofBizCheckSuccessResult();
    }
}
