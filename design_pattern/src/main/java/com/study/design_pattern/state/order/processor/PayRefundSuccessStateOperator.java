package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderStateOperator;
import com.study.design_pattern.state.order.StatusResult;
import com.study.design_pattern.state.order.annotation.OrderOperator;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
@OrderOperator
//退款成功,订单结束
public class PayRefundSuccessStateOperator extends AbstractOrderStateOperator {

    public PayRefundSuccessStateOperator() {
        setAction(OrderAction.DO_SEND_PROD_TIMEOUT_CANCEL);
    }

    // 将 lastOrderStatus 迁移至 SEND_TIMEOUT_CANCEL_WAITINGREFUND
    @Override
    protected StatusResult stateTransform(OrderStatusEnum lastOrderStatus) {
        if (lastOrderStatus != OrderStatusEnum.SEND_TIMEOUT_CANCEL_WAITINGREFUND) {
            return StatusResult.OfFailedResult("依赖的订单状态不是SEND_TIMEOUT_CANCEL_WAITINGREFUND");
        }
        return StatusResult.OfSuccessResult(getAction().getStatus());
    }
}
