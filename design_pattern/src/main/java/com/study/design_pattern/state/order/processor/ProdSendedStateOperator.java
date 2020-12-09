package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderStateOperator;
import com.study.design_pattern.state.order.StatusResult;
import com.study.design_pattern.state.order.annotation.OrderOperator;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
@OrderOperator
public class ProdSendedStateOperator extends AbstractOrderStateOperator {

    public ProdSendedStateOperator() {
        setAction(OrderAction.DO_PROD_SEND);
    }

    // 将 lastOrderStatus 迁移至 CONFIRM_FINISHED
    @Override
    protected StatusResult stateTransform(OrderStatusEnum lastOrderStatus) {
        if (lastOrderStatus != OrderStatusEnum.WAITING_SEND_PROD) {
            return StatusResult.OfFailedResult("依赖的订单状态不是WAITING_SEND_PROD");
        }
        return StatusResult.OfSuccessResult(getAction().getStatus());
    }
}
