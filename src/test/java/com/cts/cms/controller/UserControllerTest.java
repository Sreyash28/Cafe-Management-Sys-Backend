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

import com.cts.cms.model.Users;
import com.cts.cms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService service;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private UserController controller;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void test_Find_All_Users() throws Exception {

		Users user1 = new Users(11, "testuser", "8945631100", "testuser@example.com", "test323", "user", null,null);
		Users user2 = new Users(12, "testuser2", "8945632200", "testuser2@example.com", "test232", "user", null,null);
		List<Users> users = Arrays.asList(user1, user2);
		when(service.getAllUsers()).thenReturn(users);
		mockMvc.perform(get("/api/v1/users")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(users.size()));

	}

	@Test
	void test_Find_User_By_Id() throws Exception {
		int id = 11;
		Users user = new Users(id, "testuser", "8945631100", "testuser@example.com", "test323", "user", null,null);
		when(service.findUserById(id)).thenReturn(user);
		mockMvc.perform(get("/api/v1/users/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id));
	}

	@Test
	void test_Create_User() throws Exception {
		Users user = new Users(11, "testuser", "8945631100", "testuser@example.com", "test323", "user", null,null);
		when(service.createUser(user)).thenReturn(user);
		String userJson = new ObjectMapper().writeValueAsString(user);
		mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(user.getId()));
	}

	@Test
	void test_Update_User() throws Exception {
		int id = 11;
		Users user = new Users(id, "testuser4", "8945632230", "testuser@example.com", "test323", "user", null,null);
		when(service.update(eq(id), any(Users.class))).thenReturn(user);
		String json = new ObjectMapper().writeValueAsString(user);
		mockMvc.perform(put("/api/users/" + id).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.userName").value(user.getUsername()))
				.andExpect(jsonPath("$.contactNumber").value(user.getContactNumber()))
				.andExpect(jsonPath("$.email").value(user.getEmail()))
				.andExpect(jsonPath("$.password").value(user.getPassword()))
				.andExpect(jsonPath("$.role").value(user.getRole()));
	}

	@Test
	void test_Delete_User() throws Exception {
		int id = 11;
		doNothing().when(service).deleteById(id);
		mockMvc.perform(delete("/api/users/" + id)).andExpect(status().isOk());
		verify(service, times(1)).deleteById(id);
	}

}