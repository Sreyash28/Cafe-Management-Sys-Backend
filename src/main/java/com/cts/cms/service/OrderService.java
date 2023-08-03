package com.cts.cms.service;

import java.util.List;

import com.cts.cms.exception.OrderNotFoundException;
import com.cts.cms.model.Orders;

public interface OrderService {

	Orders findById(long id) throws OrderNotFoundException;

	List<Orders> findAll();

	Orders createOrder(Orders order) throws Exception;

	void deleteOrderById(long id);

	Orders update(Long orderId, Orders order);

}
