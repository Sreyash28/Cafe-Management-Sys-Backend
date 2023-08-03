package com.cts.cms.service;

import java.util.List;

import com.cts.cms.model.OrderItems;

public interface OrderItemService {
	OrderItems createOrderItem(OrderItems orderItem);

	OrderItems updateOrderItem(long orderItemId, OrderItems orderItem);

	void deleteOrderItem(long orderItemId);

	OrderItems getOrderItemById(long orderItemId);

	List<OrderItems> getAllOrderItems();
}
