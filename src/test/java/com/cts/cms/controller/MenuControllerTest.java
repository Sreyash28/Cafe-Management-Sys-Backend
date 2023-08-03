package com.cts.cms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.cms.model.Menu;
import com.cts.cms.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
 class MenuControllerTest {

	@Mock
	private MenuService service;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private MenuController controller;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void test_Find_All_Items() throws Exception {

		Menu menu1 = new Menu(11, "pizza", 40.0, null);
		Menu menu2 = new Menu(11, "burger", 30.0, null);
		List<Menu> menu = Arrays.asList(menu1, menu2);
		when(service.findAll()).thenReturn(menu);
		mockMvc.perform(get("/api/v1/items")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(menu.size()));

	}

	@Test
	void test_Find_Item_By_Id() throws Exception {
		int id = 11;
		Menu menu = new Menu(11, "pizza", 40.0, null);
		when(service.findById(id)).thenReturn(menu);
		mockMvc.perform(get("/api/v1/items/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id));
	}

	@Test
	void test_Create_Menu_Item() throws Exception {
		Menu menu = new Menu(11, "pizza", 40.0, null);
		when(service.createItems(menu)).thenReturn(menu);
		String userJson = new ObjectMapper().writeValueAsString(menu);
		mockMvc.perform(post("/api/v1/items").contentType(MediaType.APPLICATION_JSON).content(userJson))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(menu.getId()));
	}

	@Test
	void test_Update_Item_By_Id() throws Exception {
		int id = 11;
		Menu menu = new Menu(id, "chips", 20.0, null);
		when(service.update(eq(id), any(Menu.class))).thenReturn(menu);
		String json = new ObjectMapper().writeValueAsString(menu);
		mockMvc.perform(put("/api/items/" + id).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id));
	}

	@Test
	void test_Delete_Item_By_Item() throws Exception {
		int id = 11;
		doNothing().when(service).deleteById(id);
		mockMvc.perform(delete("/api/items/" + id)).andExpect(status().isOk());
		verify(service, times(1)).deleteById(id);
	}

}
