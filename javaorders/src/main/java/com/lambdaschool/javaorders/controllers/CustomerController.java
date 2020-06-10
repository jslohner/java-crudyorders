package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/orders", produces = {"application/json"})
	public ResponseEntity<?> findAllCustomers() {
		List<Customer> allCustomers = customerService.findAllCustomers();
		return new ResponseEntity<>(allCustomers, HttpStatus.OK);
	}

	@GetMapping(value = "/customer/{custId}", produces = {"application/json"})
	public ResponseEntity<?> findCustomerById(@PathVariable long custId) {
		Customer customer = customerService.findCustomerById(custId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@GetMapping(value = "/namelike/{likename}", produces = {"application/json"})
	public ResponseEntity<?> listAllCustomersLikeName(@PathVariable String likename) {
		List<Customer> likeNameCustomers = customerService.findByNameLike(likename);
		return new ResponseEntity<>(likeNameCustomers, HttpStatus.OK);
	}
}
