package com.cts.cms.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
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

import com.cts.cms.dto.LoginDto;
import com.cts.cms.dto.LoginResponseDto;
import com.cts.cms.dto.RegisterDto;
import com.cts.cms.exception.AuthorizationException;
import com.cts.cms.model.Users;
import com.cts.cms.service.UserService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

	@Autowired
	UserService service;

	private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);

	/**
	 * this will return all users
	 * 
	 * @return
	 */

	@GetMapping("/v1/users")
	public ResponseEntity<List<Users>> findAll() {
		log.info(" /v1/users api endpoint called ");
		return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
	}

	/**
	 * this will return the user of respected id
	 * 
	 * @param id
	 */

	@GetMapping("/v1/users/{id}")
	public ResponseEntity<Users> findByIdV1(@PathVariable("id") int id) {
		log.info(" /v1/users api called with user ID {} ", id);
		return new ResponseEntity<>(service.findUserById(id), HttpStatus.OK);
	}

	/**
	 * this will create new user
	 * 
	 * @param user
	 * @return
	 */

	@PostMapping("/v1/users")
	public ResponseEntity<Users> create(@RequestBody Users user) {
		ResponseEntity<Users> responseEntity;
		responseEntity = new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
		log.info(" /v1/users API called with User data {}", user);
		return responseEntity;
	}

//	/**
//	 * this will register the user
//	 * 
//	 * @param registerDto
//	 * @return
//	 */
	@PostMapping("/users/register")
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
		return service.register(registerDto);
	}

//
//	/**
//	 * 
//	 * @param loginDto
//	 * @return
//	 * @throws AuthorizationException
//	 */
	@PostMapping("/users/authenticate")
	public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginDto loginDto) throws AuthorizationException {
		return new ResponseEntity<>(service.authenticate(loginDto), HttpStatus.OK);
	}

	/**
	 * this will delete the exsting user
	 * 
	 * @param id
	 */

	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable("id") int id) {
		log.info(" /users API called for deletion of User data with ID {}", id);
		service.deleteById(id);
	}

	/**
	 * this will update the user with respected id
	 * 
	 * @param id
	 * @param user
	 * @return
	 */

	@PutMapping("/users/{id}")
	public Users update(@PathVariable("id") int id, @RequestBody Users user) {
		log.info(" /users API called for updatethe user data {}", id);
		return service.update(id, user);
	}

}
