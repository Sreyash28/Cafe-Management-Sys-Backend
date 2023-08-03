package com.cts.cms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.cms.exception.MenuItemNotFoundException;
import com.cts.cms.exception.OrderNotFoundException;
import com.cts.cms.exception.UserNotFoundException;
import com.cts.cms.model.Menu;
import com.cts.cms.model.OrderItems;
import com.cts.cms.model.Orders;
import com.cts.cms.model.Users;
import com.cts.cms.repository.MenuRepository;
import com.cts.cms.repository.OrdersRepository;
import com.cts.cms.repository.UserRepository;

import ch.qos.logback.classic.Logger;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrdersRepository repository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MenuRepository menuRepository;

	@Override
	public Orders createOrder(Orders order) throws Exception {
		logger.debug("Creating order: {}", order);

		// Check if user exists
		Optional<Users> optionalUser = userRepository.findById(order.getUser().getId());
		if (optionalUser.isEmpty()) {
			logger.warn("User not found with ID: {}", order.getUser().getId());
			throw new UserNotFoundException("User not found with ID: " + order.getUser().getId());
		}

		// Assign user to the order object
		Users user = optionalUser.get();
		order.setUser(user);

		// Calculate total amount
		double totalAmount = 0.0;
		for (OrderItems orderItem : order.getOrderItems()) {
			Optional<Menu> optionalMenuItem = menuRepository.findById(orderItem.getMenu().getId());
			if (optionalMenuItem.isEmpty()) {
				logger.warn("Item not found with ID: {}", orderItem.getMenu().getId());
				throw new MenuItemNotFoundException("Menu item not found with ID: " + orderItem.getMenu().getId());
			}
			Menu menuItem = optionalMenuItem.get();
			orderItem.setMenu(menuItem); // Set the retrieved Menu object in OrderItems

			double itemTotal = menuItem.getItemPrice() * orderItem.getQuantity();
			totalAmount += itemTotal;
		}

		order.setTotalPrice(totalAmount);
		order.setOrderDate(LocalDateTime.now());

		// Save the order
		Orders savedOrder = repository.save(order);
		logger.debug("Order created successfully with ID: {}", savedOrder.getId());
		return savedOrder;
	}

//	@Override
//	public Orders createOrder(Orders order) throws Exception {
//
//		logger.debug("Creating order: {}", order);
//
//		// Check if user exists
//		Optional<Users> optionalUser = userRepository.findById(order.getUser().getId());
//		if (optionalUser.isEmpty()) {
//			logger.warn("User not found with ID: {}", order.getUser().getId());
//			throw new UserNotFoundException("User not found with ID: " + order.getUser().getId());
//		}
//
//		// Assign user to the order object
//		Users user = optionalUser.get();
//		order.setUser(user);
//
//		// Retrieve and assign menu items to the order object
//		List<Menu> menuItems = new ArrayList<>();
//		for (Menu menuItem : order.getMenuItem()) {
//			Optional<Menu> optionalMenuItem = menuRepository.findById(menuItem.getId());
//			if (optionalMenuItem.isEmpty()) {
//				logger.warn("Item not found with ID: {}", menuItem.getId());
//				throw new MenuItemNotFoundException("Menu item not found with ID: " + menuItem.getId());
//			}
//			menuItems.add(optionalMenuItem.get());
//		}
//		order.setMenuItem(menuItems);
//
//		// Set order details
//		order.setQuantity(order.getQuantity());
//		double totalAmount = 0.0;
//		for (Menu menuItem : menuItems) {
//			totalAmount += menuItem.getPrice() * order.getQuantity();
//		}
//		order.setTotalAmount(totalAmount);
//		order.setDateTime(LocalDateTime.now());
//
//		// Save the order
//		Orders savedOrder = repository.save(order);
//		logger.debug("Order created successfully with ID: {}", savedOrder.getOrderId());
//		return savedOrder;
//	}
//	
//	

	@Override
	public List<Orders> findAll() {
		logger.info("Finding all items");
		return repository.findAll();
	}

	@Override
	public Orders findById(long id) throws OrderNotFoundException {
		Optional<Orders> optional = repository.findById(id);
		if (optional.isEmpty()) {
			logger.warn("Order not found with ID: {}", id);
			throw new OrderNotFoundException("order not found with ID: " + id);
		}
		return optional.get();
	}

	@Override
	public void deleteOrderById(long id) {
		logger.info("Deleting item by ID: {}", id);
		Optional<Orders> optionalOrder = repository.findById(id);

		if (optionalOrder.isPresent()) {
			repository.deleteById(id);
			logger.info("Deleted item with ID: {}", id);
		} else {
			logger.warn("Order not found with ID: {}", id);
			throw new OrderNotFoundException("Order not found with ID: " + id);
		}
	}

//	@Override
//	public Orders update(long id, Orders newOrderData) {
//		Orders existingOrder = findById(id);
//
//		double price = existingOrder.getMenuItem().getPrice();
//		int quantity = newOrderData.getQuantity();
//		double totalAmount = price * quantity;
//
//		existingOrder.setQuantity(quantity);
//		existingOrder.setTotalAmount(totalAmount);
//		existingOrder.setDateTime(LocalDateTime.now());
//
//		Orders updatedOrder = repository.save(existingOrder);
//		logger.debug("Order updated successfully with ID: {}", updatedOrder.getOrderId());
//		return updatedOrder;
//	}

	@Override
	public Orders update(Long orderId, Orders order) {
		Orders existingOrder = repository.findById(orderId).orElse(null);
		if (existingOrder != null) {
			existingOrder.setUser(order.getUser());
			existingOrder.setOrderDate(order.getOrderDate());
			existingOrder.setTotalPrice(order.getTotalPrice());

			// Clear existing order items
			existingOrder.getOrderItems().clear();

			// Add updated order items
			for (OrderItems updatedOrderItem : order.getOrderItems()) {
				OrderItems existingOrderItem = new OrderItems();
				existingOrderItem.setOrder(existingOrder);
				existingOrderItem.setMenu(updatedOrderItem.getMenu());
				existingOrderItem.setQuantity(updatedOrderItem.getQuantity());
				existingOrder.getOrderItems().add(existingOrderItem);
			}

			return repository.save(existingOrder);
		}
		return null;
	}
}
