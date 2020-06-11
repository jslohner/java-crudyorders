package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;

public interface OrderService {
	Order findOrderById(long id);

	void delete(long id);

	Order save(Order order);
}
