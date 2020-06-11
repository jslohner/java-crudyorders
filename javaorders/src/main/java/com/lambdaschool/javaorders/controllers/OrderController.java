package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/order/{ordnum}", produces = {"application/json"})
	public ResponseEntity<?> findOrderById(@PathVariable long ordnum) {
		Order order = orderService.findOrderById(ordnum);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PostMapping(value = "/order", consumes = {"application/json"})
	public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder) {
		newOrder.setOrdnum(0);
		newOrder = orderService.save(newOrder);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{ordnum}")
				.buildAndExpand(newOrder.getOrdnum())
				.toUri();
		responseHeaders.setLocation(newOrderURI);

		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@PutMapping(value = "/order/{ordnum}", consumes = {"application/json"})
	public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updateOrder, @PathVariable long ordnum) {
		updateOrder.setOrdnum(ordnum);
		orderService.save(updateOrder);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/order/{ordnum}")
	public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum) {
		orderService.delete(ordnum);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
