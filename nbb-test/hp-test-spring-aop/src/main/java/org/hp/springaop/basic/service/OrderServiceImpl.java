package org.hp.springaop.basic.service;

import org.hp.springaop.basic.model.Order;

public class OrderServiceImpl implements OrderService{

    private static Order order = null;

    @Override
    public Order createOrder(String userName, String product) {
        return new Order(userName, product);
    }

    @Override
    public Order queryOrder() {
        return order;
    }
}
