package com.study.design_pattern.state.order.processor;


import com.study.design_pattern.state.order.AbstractOrderStateOperator;
import com.study.design_pattern.state.order.StatusResult;
import com.study.design_pattern.state.order.annotation.OrderOperator;
import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.stereotype.Component;

@Component
@OrderOperator
//订单确认超时，自动签收订单
public class OrderConfirmTimeoutStateOperator extends AbstractOrderStateOperator {

    public OrderConfirmTimeoutStateOperator() {
        setAction(OrderAction.DO_SENDED_ORDER_AUTO_COMPLETE);
    }


    // 将 lastOrderStatus 迁移至 PROD_SENDED
    @Override
    protected StatusResult stateTransform(OrderStatusEnum lastOrderStatus) {
        if (lastOrderStatus != OrderStatusEnum.PROD_SENDED) {
            return StatusResult.OfFailedResult("依赖的订单状态不是PROD_SENDED");
        }
        return StatusResult.OfSuccessResult(getAction().getStatus());
    }
}
