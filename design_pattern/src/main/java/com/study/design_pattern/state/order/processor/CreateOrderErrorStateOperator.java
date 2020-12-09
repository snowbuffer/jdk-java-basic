package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderStateOperator;
import com.study.design_pattern.state.order.StatusResult;
import com.study.design_pattern.state.order.annotation.OrderOperator;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
@OrderOperator
public class CreateOrderErrorStateOperator extends AbstractOrderStateOperator {
    public CreateOrderErrorStateOperator() {
        setAction(OrderAction.DO_CREATE_ERROR);
    }

    // 将 lastOrderStatus 迁移至 INVISIBLE
    @Override
    protected StatusResult stateTransform(OrderStatusEnum lastOrderStatus) {
        if (lastOrderStatus != OrderStatusEnum.INVISIBLE) {
            return StatusResult.OfFailedResult("依赖的订单状态不是INVISIBLE");
        }
        return StatusResult.OfSuccessResult(getAction().getStatus());
    }
}
