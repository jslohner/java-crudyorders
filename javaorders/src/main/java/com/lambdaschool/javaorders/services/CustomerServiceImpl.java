package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
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

	// POST - PUT
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

			newOrder.getPayments().clear();
			for (Payment p : o.getPayments()) {
				newOrder.addPayment(p);
			}

			newCustomer.getOrders()
					.add(newOrder);
		}

		return custrepo.save(newCustomer);
	}

	// PATCH
	@Transactional
	@Override
	public Customer update(Customer customer, long id) {
		Customer currentCustomer = custrepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));

		if (customer.getCustname() != null) {
			currentCustomer.setCustname(customer.getCustname());
		}
		if (customer.getCustcity() != null) {
			currentCustomer.setCustcity(customer.getCustcity());
		}
		if (customer.getWorkingarea() != null) {
			currentCustomer.setWorkingarea(customer.getWorkingarea());
		}
		if (customer.getCustcountry() != null) {
			currentCustomer.setCustcountry(customer.getCustcountry());
		}
		if (customer.getGrade() != null) {
			currentCustomer.setGrade(customer.getGrade());
		}
		if (customer.hasvalueforopeningamt) {
			currentCustomer.setOpeningamt(customer.getOpeningamt());
		}
		if (customer.hasvalueforreceiveamt) {
			currentCustomer.setReceiveamt(customer.getReceiveamt());
		}
		if (customer.hasvalueforpaymentamt) {
			currentCustomer.setPaymentamt(customer.getPaymentamt());
		}
		if (customer.hasvalueforoutstandingamt) {
			currentCustomer.setOutstandingamt(customer.getOutstandingamt());
		}
		if (customer.getPhone() != null) {
			currentCustomer.setPhone(customer.getPhone());
		}
		if (customer.getAgent() != null) {
			currentCustomer.setAgent(customer.getAgent());
		}
		if (customer.getOrders().size() > 0) {
			currentCustomer.getOrders().clear();
			for (Order o : customer.getOrders()) {
				Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), currentCustomer, o.getOrderdescription());

				newOrder.getPayments().clear();
				for (Payment p : o.getPayments()) {
					newOrder.addPayment(p);
				}

				currentCustomer.getOrders()
						.add(newOrder);
			}
		}
		return custrepo.save(currentCustomer);
	}

	// DELETE
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
