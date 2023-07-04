package org.hp.springaop.basic.service;

import org.hp.springaop.basic.model.Order;

public interface OrderService {

    Order createOrder(String userName, String product);

    Order queryOrder();
}
