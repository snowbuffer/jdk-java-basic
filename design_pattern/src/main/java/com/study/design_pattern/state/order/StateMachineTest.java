package com.study.design_pattern.state.order;


import com.study.design_pattern.state.order.state.OrderAction;
import com.study.design_pattern.state.order.state.OrderStatusEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StateMachineTest {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        OrderStateManager bean = context.getBean(OrderStateManager.class);
        bean.handleOrder(123L, OrderAction.DO_PRE_OCCUPY_STORE, OrderStatusEnum.INVISIBLE, null);
    }
}
