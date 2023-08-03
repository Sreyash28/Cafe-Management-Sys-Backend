package com.cts.cms.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.cms.exception.OrderItemNotFoundException;
import com.cts.cms.model.OrderItems;
import com.cts.cms.repository.OrderItemRepository;

import ch.qos.logback.classic.Logger;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderItemServiceImpl.class);

	@Autowired
	OrderItemRepository orderItemsRepository;

	@Override
	public OrderItems createOrderItem(OrderItems orderItem) {
		return orderItemsRepository.save(orderItem);
	}

	@Override
	public OrderItems updateOrderItem(long orderItemId, OrderItems orderItem) {
		OrderItems existingOrderItem = orderItemsRepository.findById(orderItemId).orElse(null);
		if (existingOrderItem != null) {
			existingOrderItem.setOrder(orderItem.getOrder());
			existingOrderItem.setMenu(orderItem.getMenu());
			existingOrderItem.setQuantity(orderItem.getQuantity());
			return orderItemsRepository.save(existingOrderItem);
		}
		return null;
	}

	@Override
	public void deleteOrderItem(long orderItemId) {
		orderItemsRepository.deleteById(orderItemId);
	}

	@Override
	public OrderItems getOrderItemById(long orderItemId) throws OrderItemNotFoundException {
		Optional<OrderItems> optionalOrderItem = orderItemsRepository.findById(orderItemId);
		if (optionalOrderItem.isEmpty()) {
			logger.warn("Order item not found with ID: {}", orderItemId);
			throw new OrderItemNotFoundException("Order item not found with ID: " + orderItemId);
		}
		return optionalOrderItem.get();
	}

	@Override
	public List<OrderItems> getAllOrderItems() {
		return orderItemsRepository.findAll();
	}
}
