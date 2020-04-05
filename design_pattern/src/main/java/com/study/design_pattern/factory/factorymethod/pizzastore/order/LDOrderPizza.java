package com.study.design_pattern.factory.factorymethod.pizzastore.order;

import com.study.design_pattern.factory.factorymethod.pizzastore.pizza.BJCheesePizza;
import com.study.design_pattern.factory.factorymethod.pizzastore.pizza.BJPepperPizza;
import com.study.design_pattern.factory.factorymethod.pizzastore.pizza.LDCheesePizza;
import com.study.design_pattern.factory.factorymethod.pizzastore.pizza.LDPepperPizza;
import com.study.design_pattern.factory.factorymethod.pizzastore.pizza.Pizza;


public class LDOrderPizza extends OrderPizza {

	
	@Override
	Pizza createPizza(String orderType) {
	
		Pizza pizza = null;
		if(orderType.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (orderType.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		// TODO Auto-generated method stub
		return pizza;
	}

}
