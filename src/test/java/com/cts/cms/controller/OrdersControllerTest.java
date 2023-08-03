package com.cts.cms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.cms.model.Menu;
import com.cts.cms.model.Orders;
import com.cts.cms.model.Users;
import com.cts.cms.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrdersController orderController;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	void test_FindAll_Orders() throws Exception {
		List<Orders> ordersList = new ArrayList<>();
		Orders order1 = new Orders();
		order1.setOrderId(1);
		order1.setUser(new Users(1, "John", "8945631100", "testuser@example.com", "test323", "user", null,null));
		order1.setMenuItem(new Menu(1, "Pizza", 10.5, null));
		order1.setQuantity(2);
		order1.setTotalAmount(21.0);
		ordersList.add(order1);

		Orders order2 = new Orders();
		order2.setOrderId(2);
		order2.setUser(new Users(2, "Alice", "8945631500", "testuser2@example.com", "test22", "user", null,null));
		order2.setMenuItem(new Menu(2, "Burger", 7.5, null));
		order2.setQuantity(1);
		order2.setTotalAmount(7.5);
		ordersList.add(order2);

		when(orderService.findAll()).thenReturn(ordersList);

		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

		mockMvc.perform(get("/api/v1/orders")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath("$.[0].menuItem.id").value(1))
				.andExpect(jsonPath("$.[0].menuItem.name").value("Pizza"))
				.andExpect(jsonPath("$.[1].menuItem.id").value(2))
				.andExpect(jsonPath("$.[1].menuItem.name").value("Burger"));

	}

	@Test
	void test_Find_Orser_By_Id() {

		Orders order1 = new Orders();
		order1.setOrderId(1L);
		order1.setUser(new Users(1, "John", "8945631100", "testuser@example.com", "test323", "user", null,null));
		order1.setMenuItem(new Menu(1, "Pizza", 10.5, null));
		order1.setQuantity(2);
		order1.setTotalAmount(21.0);

		when(orderService.findById(anyLong())).thenReturn(order1);

		ResponseEntity<Orders> responseEntity = orderController.findById(1L);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(order1, responseEntity.getBody());

		verify(orderService).findById(1L);
	}

	@Test
	void test_Create_Order() throws Exception {
		String orderJson = "{\"userId\": 1, \"menuItemId\": 1, \"quantity\": 2}";

		mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(orderJson))
				.andExpect(status().isCreated());
	}

	@Test
	void test_delete_Order_ById() throws Exception {
		long id = 1L;
		doNothing().when(orderService).deleteOrderById(id);
		mockMvc.perform(delete("/api/orders/{id}", id)).andExpect(status().isOk());
		verify(orderService, times(1)).deleteOrderById(id);
		verifyNoMoreInteractions(orderService);
	}

	@Test
	void test_update_Order_by_Id() throws Exception {
		Orders existingOrder = new Orders();
		existingOrder.setOrderId(1);
		existingOrder.setQuantity(2);
		existingOrder.setTotalAmount(10.0);

		Orders updatedOrder = new Orders();
		updatedOrder.setOrderId(1);
		updatedOrder.setQuantity(3);
		updatedOrder.setTotalAmount(15.0);

		given(orderService.update(1L, updatedOrder)).willReturn(updatedOrder);

		mockMvc.perform(put("/api/orders/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedOrder))).andExpect(status().isOk())
				.andExpect(jsonPath("$.orderId").value(1)).andExpect(jsonPath("$.quantity").value(3))
				.andExpect(jsonPath("$.totalAmount").value(15.0));
	}

}
