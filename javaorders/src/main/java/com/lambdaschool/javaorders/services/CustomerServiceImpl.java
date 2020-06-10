package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
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
