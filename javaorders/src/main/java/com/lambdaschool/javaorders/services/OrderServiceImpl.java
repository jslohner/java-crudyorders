package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository ordrepo;

	@Override
	public Order findOrderById(long id) {
		return ordrepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
	}
}
