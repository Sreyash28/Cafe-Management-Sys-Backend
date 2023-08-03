package com.cts.cms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.cms.exception.MenuItemNotFoundException;
import com.cts.cms.exception.OrderNotFoundException;
import com.cts.cms.exception.UserNotFoundException;
import com.cts.cms.model.Menu;
import com.cts.cms.model.Orders;
import com.cts.cms.model.Users;
import com.cts.cms.repository.MenuRepository;
import com.cts.cms.repository.OrdersRepository;
import com.cts.cms.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@Mock
	private OrdersRepository repository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MenuRepository menuRepository;

	@InjectMocks
	private OrderServiceImpl orderService;

	private Orders order;
	private Users user;
	private Menu menuItem;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		user = new Users(12, "testuser", "8945631100", "testuser@example.com", "test323", "user", null, null);
		menuItem = new Menu(1, "Cheeseburger", 10.99, null);
		order = new Orders(1L, user, menuItem, 2, 21.98, LocalDateTime.now());
	}

	@Test
		 void test_Create_Order() throws Exception {
		        // Arrange
		        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		        when(menuRepository.findById(menuItem.getId())).thenReturn(Optional.of(menuItem));
		        when(repository.save(order)).thenReturn(order);

		        Orders createdOrder = orderService.createOrder(order);

		        assertEquals(order, createdOrder);
		        verify(repository, times(1)).save(order);
		    }

	@Test
     void test_Create_Order_With_Non_Existent_User() {
        
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        
        assertThrows(UserNotFoundException.class, () -> {
            orderService.createOrder(order);
        });
        verify(repository, times(0)).save(order);
    }

	@Test
    void test_Create_Order_With_Existent_MenuItem() {
        
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(menuRepository.findById(menuItem.getId())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> {
            orderService.createOrder(order);
        });
        verify(repository, times(0)).save(order);
    }

	@Test
	void test_Find_All_Orders() {
		List<Orders> orders = Arrays.asList(order);
		when(repository.findAll()).thenReturn(orders);

		List<Orders> foundOrders = orderService.findAll();

		assertEquals(orders, foundOrders);
	}

	@Test
	void test_Delete_Order_By_Id() {
		Long orderId = 1L;
		Mockito.when(repository.findById(orderId)).thenReturn(Optional.of(new Orders()));

		orderService.deleteOrderById(orderId);

		Mockito.verify(repository, Mockito.times(1)).deleteById(orderId);
	}

	@Test
	void test_Delete_Order_By_Id_When_Order_NotFound() {
		Long orderId = 1L;
		Mockito.when(repository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(OrderNotFoundException.class, () -> {
			orderService.deleteOrderById(orderId);
		});

		Mockito.verify(repository, Mockito.times(0)).deleteById(orderId);
	}

	@Test
	void test_Update_Existing_Order() {
		Users user = new Users(12, "testuser", "8945631100", "testuser@example.com", "test323", "user", null, null);
		Menu menuItem = new Menu(1, "Cheeseburger", 10.99, null);
		Orders existingOrder = new Orders(1L, user, menuItem, 2, 21.98, LocalDateTime.now());
		Orders newOrderData = new Orders();
		newOrderData.setQuantity(3);

		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existingOrder));
		Mockito.when(repository.save(existingOrder)).thenReturn(existingOrder);

		Orders updatedOrder = orderService.update(1L, newOrderData);
		assertEquals(1L, updatedOrder.getOrderId());
		assertEquals(3, updatedOrder.getQuantity());
		assertEquals(menuItem.getPrice() * 3, updatedOrder.getTotalAmount());
		assertNotNull(updatedOrder.getDateTime());
		assertEquals(user, updatedOrder.getUser());
		assertEquals(menuItem, updatedOrder.getMenuItem());

		Mockito.verify(repository, Mockito.times(1)).findById(1L);
		Mockito.verify(repository, Mockito.times(1)).save(existingOrder);
	}

	@Test
	void test_Update_When_Order_NotFound() {
		Long orderId = 1L;
		Orders newOrderData = new Orders();
		newOrderData.setQuantity(3);

		Mockito.when(repository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(OrderNotFoundException.class, () -> {
			orderService.update(orderId, newOrderData);
		});

		Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
	}

}
