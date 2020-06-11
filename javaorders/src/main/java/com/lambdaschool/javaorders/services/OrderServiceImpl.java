package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// POST - PUT
	@Transactional
	@Override
	public Order save(Order order) {
		Order newOrder = new Order();

		if (order.getOrdnum() != 0) {
			ordrepo.findById(order.getOrdnum())
					.orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found"));

			newOrder.setOrdnum(order.getOrdnum());
		}

		newOrder.setOrdamount(order.getOrdamount());
		newOrder.setAdvanceamount(order.getAdvanceamount());
		newOrder.setOrderdescription(order.getOrderdescription());

		newOrder.setCustomer(order.getCustomer());

		newOrder.getPayments().clear();
		for (Payment p : order.getPayments()) {
			newOrder.addPayment(p);
		}

		return ordrepo.save(newOrder);
	}

	// DELETE
	@Transactional
	@Override
	public void delete(long id) {
		if (ordrepo.findById(id).isPresent()) {
			ordrepo.deleteById(id);
		} else {
			throw new EntityNotFoundException("Order " + id + " Not Found");
		}
	}
}
