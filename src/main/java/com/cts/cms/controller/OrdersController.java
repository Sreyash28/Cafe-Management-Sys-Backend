package com.cts.cms.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.cms.model.Orders;
import com.cts.cms.service.OrderService;

import ch.qos.logback.classic.Logger;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api")
public class OrdersController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(OrdersController.class);

	@Autowired
	OrderService service;

	/**
	 * this will return all the orders present in orders
	 * 
	 * @return
	 */

	@GetMapping("/v1/orders")
	public ResponseEntity<List<Orders>> findAll() {
		log.info(" /v1/orders api endpoint called ");
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	/**
	 * this will return order of respected id
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping("/v1/orders/{id}")
	public ResponseEntity<Orders> findById(@PathVariable("id") long id) {
		log.info(" /v1/users api called with order ID {} ", id);
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	/**
	 * this will create the order
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */

	@PostMapping("/v1/orders")
	public ResponseEntity<Orders> create(@RequestBody Orders order) throws Exception {
		ResponseEntity<Orders> responseEntity;
		responseEntity = new ResponseEntity<>(service.createOrder(order), HttpStatus.CREATED);
		log.info(" /v1/users API called with order data {}", order);
		return responseEntity;
	}

	/**
	 * this will delete the order of respected id
	 * 
	 * @param id
	 */

	@DeleteMapping("/orders/{id}")
	public void deleteOrderById(@PathVariable("id") long id) {
		log.info(" /users API called for deletion of order data with ID {}", id);
		service.deleteOrderById(id);
	}

	/**
	 * this will update the order
	 * 
	 * @param id
	 * @param order
	 * @return
	 */

	@PutMapping("/orders/{id}")
	public Orders update(@PathVariable("id") long id, @RequestBody Orders order) {
		log.info(" /orders API called for update the order data {}", id);
		return service.update(id, order);
	}

}
