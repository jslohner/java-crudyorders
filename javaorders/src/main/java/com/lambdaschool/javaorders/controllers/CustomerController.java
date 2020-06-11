package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

	@PostMapping(value = "/customer", consumes = {"application/json"})
	public ResponseEntity<?> addNewCustomer(
			@Valid
			@RequestBody
				Customer newCustomer
	) {
		newCustomer.setCustcode(0);
		newCustomer = customerService.save(newCustomer);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{custcode}")
				.buildAndExpand(newCustomer.getCustcode())
				.toUri();
		responseHeaders.setLocation(newCustomerURI);

		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@PutMapping(value = "/customer/{custId}", consumes = {"application/json"})
	public ResponseEntity<?> updateFullCustomer(
			@Valid
			@RequestBody
				Customer updateCustomer,
			@PathVariable
				long custId
	) {
		updateCustomer.setCustcode(custId);
		customerService.save(updateCustomer);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/customer/{custId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable long custId) {
		customerService.delete(custId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
