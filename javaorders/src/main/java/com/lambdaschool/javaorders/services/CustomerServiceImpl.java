package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository custrepo;

	@Override
	public List<Customer> findAllCustomers() {
		List<Customer> rtnList = new ArrayList<>();

		custrepo.findAll()
				.iterator()
				.forEachRemaining(rtnList::add);

		return rtnList;
	}

	@Override
	public Customer findCustomerById(long id) {
		return custrepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
	}

	@Override
	public List<Customer> findByNameLike(String likename) {
		return custrepo.findByCustnameContainingIgnoringCase(likename);
	}

	// POST - PUT - PATCH
	@Transactional
	@Override
	public Customer save(Customer customer) {
		Customer newCustomer = new Customer();

		if (customer.getCustcode() != 0) {
			custrepo.findById(customer.getCustcode())
					.orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));

			newCustomer.setCustcode(customer.getCustcode());
		}

		newCustomer.setCustname(customer.getCustname());
		newCustomer.setCustcity(customer.getCustcity());
		newCustomer.setWorkingarea(customer.getWorkingarea());
		newCustomer.setCustcountry(customer.getCustcountry());
		newCustomer.setGrade(customer.getGrade());
		newCustomer.setOpeningamt(customer.getOpeningamt());
		newCustomer.setReceiveamt(customer.getReceiveamt());
		newCustomer.setPaymentamt(customer.getPaymentamt());
		newCustomer.setOutstandingamt(customer.getOutstandingamt());
		newCustomer.setPhone(customer.getPhone());

		newCustomer.setAgent(customer.getAgent());

		newCustomer.getOrders().clear();
		for (Order o : customer.getOrders()) {
			Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), newCustomer, o.getOrderdescription());

			newCustomer.getOrders()
					.add(newOrder);
		}

		return custrepo.save(newCustomer);
	}

	@Transactional
	@Override
	public Customer update(Customer customer, long id) {
		return null;
	}

	@Transactional
	@Override
	public void delete(long id) {
		if (custrepo.findById(id).isPresent()) {
			custrepo.deleteById(id);
		} else {
			throw new EntityNotFoundException("Customer " + id + " Not Found");
		}
	}
}
