package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderStateOperator;
import com.study.design_pattern.state.order.StatusResult;
import com.study.design_pattern.state.order.annotation.OrderOperator;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
@OrderOperator
public class SendTimeoutCancellReviewStateOperator extends AbstractOrderStateOperator {

    public SendTimeoutCancellReviewStateOperator() {
        setAction(OrderAction.DO_SEND_PROD_TIMEOUT_CANCEL_WAITINGREFUND);
    }

    // 将 lastOrderStatus 迁移至 WAITING_SEND_PROD
    @Override
    protected StatusResult stateTransform(OrderStatusEnum lastOrderStatus) {
        if (lastOrderStatus != OrderStatusEnum.WAITING_SEND_PROD) {
            return StatusResult.OfFailedResult("依赖的订单状态不是WAITING_SEND_PROD");
        }
        return StatusResult.OfSuccessResult(getAction().getStatus());
    }
}
