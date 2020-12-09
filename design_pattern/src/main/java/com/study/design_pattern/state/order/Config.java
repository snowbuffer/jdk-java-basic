package com.study.design_pattern.state.order;

import com.study.design_pattern.state.order.annotation.OrderOperator;
import com.study.design_pattern.state.order.annotation.OrderProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 需要放在业务的订单服务工程里
 */
@ComponentScan(value = "com.study.design_pattern.state.order")
@Configuration
public class Config {

    @Bean
    public static BeanPostProcessor initialization() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return null;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof AbstractOrderStateOperator && bean.getClass().isAnnotationPresent(OrderOperator.class)) {
                    AbstractOrderStateOperator orderState = (AbstractOrderStateOperator) bean;
                    OrderStateManager.orderOperatorMaps.put(orderState.getAction(), orderState);
                }
                if (bean instanceof AbstractOrderProcessor && bean.getClass().isAnnotationPresent(OrderProcessor.class)) {
                    AbstractOrderProcessor orderProcessor = (AbstractOrderProcessor) bean;
                    OrderStateManager.orderProcessorMaps.put(orderProcessor.getOrderAction(), orderProcessor);
                }
                return bean;
            }
        };
    }

    @Bean
    public OrderStateManager orderStateManager() {
        return new OrderStateManager();
    }
}
