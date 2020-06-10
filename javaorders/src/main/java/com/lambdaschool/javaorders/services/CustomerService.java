package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;

import java.util.List;

public interface CustomerService {
	List<Customer> findAllCustomers();

	Customer findCustomerById(long id);

	List<Customer> findByNameLike(String likename);
}
