package com.cts.cms.controller;

import java.util.List;

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

import com.cts.cms.model.OrderItems;
import com.cts.cms.service.OrderItemService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class OrderItemController {

	@Autowired
	OrderItemService orderItemsService;

	/**
	 * this will create the orderitem
	 * 
	 * @param orderItem
	 * @return
	 */

	@PostMapping("/v1/orderitems")
	public ResponseEntity<OrderItems> createOrderItem(@RequestBody OrderItems orderItem) {
		OrderItems createdOrderItem = orderItemsService.createOrderItem(orderItem);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
	}

	/***
	 * this will update the orderitem
	 * 
	 * @param orderItemId
	 * @param orderItem
	 * @return
	 */
	@PutMapping("/{orderItemId}")
	public ResponseEntity<OrderItems> updateOrderItem(@PathVariable long orderItemId,
			@RequestBody OrderItems orderItem) {
		OrderItems updatedOrderItem = orderItemsService.updateOrderItem(orderItemId, orderItem);
		if (updatedOrderItem != null) {
			return ResponseEntity.ok(updatedOrderItem);
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * this will delete the orderitem
	 * 
	 * @param orderItemId
	 * @return
	 */

	@DeleteMapping("/{orderItemId}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable long orderItemId) {
		orderItemsService.deleteOrderItem(orderItemId);
		return ResponseEntity.noContent().build();
	}

	/**
	 * this will return orderitem of given id
	 * 
	 * @param orderItemId
	 * @return
	 */

	@GetMapping("/v1/orderitems/{orderItemId}")
	public ResponseEntity<OrderItems> getOrderItemById(@PathVariable long orderItemId) {
		OrderItems orderItem = orderItemsService.getOrderItemById(orderItemId);
		if (orderItem != null) {
			return ResponseEntity.ok(orderItem);
		}
		return ResponseEntity.notFound().build();
	}

	/***
	 * this will return all orderitems
	 * 
	 * @return
	 */
	@GetMapping("/v1/orderitems")
	public ResponseEntity<List<OrderItems>> getAllOrderItems() {
		List<OrderItems> orderItems = orderItemsService.getAllOrderItems();
		return ResponseEntity.ok(orderItems);
	}
}
