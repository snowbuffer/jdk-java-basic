package com.study.design_pattern.state.order.processor;

import com.study.design_pattern.state.order.AbstractOrderStateOperator;
import com.study.design_pattern.state.order.StatusResult;
import com.study.design_pattern.state.order.annotation.OrderOperator;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
@OrderOperator
public class WaitingPayStateOperator extends AbstractOrderStateOperator {

    public WaitingPayStateOperator() {
        setAction(OrderAction.DO_PRE_OCCUPY_STORE);
    }

    // 将 lastOrderStatus 迁移至 WAITING_PAY
    @Override
    protected StatusResult stateTransform(OrderStatusEnum lastOrderStatus) {
        if (lastOrderStatus != OrderStatusEnum.INVISIBLE) {
            return StatusResult.OfFailedResult("依赖的订单状态不是INVISIBLE");
        }
        return StatusResult.OfSuccessResult(getAction().getStatus());
    }
}
