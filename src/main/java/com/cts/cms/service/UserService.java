package com.cts.cms.service;

import java.util.List;


import org.springframework.http.ResponseEntity;

import com.cts.cms.dto.LoginDto;
import com.cts.cms.model.Role;
import com.cts.cms.dto.LoginResponseDto;
import com.cts.cms.dto.RegisterDto;
import com.cts.cms.exception.UserNotFoundException;
import com.cts.cms.model.Users;

public interface UserService {

	Users createUser(Users user);

	Users findUserById(int id) throws UserNotFoundException;

	void deleteById(int id) throws UserNotFoundException;

	Users update(int id, Users newuser);

	List<Users> getAllUsers();

	LoginResponseDto authenticate(LoginDto loginDto);

	ResponseEntity<?> register(RegisterDto registerDto);

	Role saveRole(Role role);


	

}
