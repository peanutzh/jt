package com.jt.web.service;

import com.jt.common.po.Order;

public interface OrderService {

	String saveOrder(Order order);

	Order findOrderById(String id);

}
