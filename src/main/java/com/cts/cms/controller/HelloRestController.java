package com.cts.cms.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/hello")
public class HelloRestController {

	@GetMapping("/user")
	public String helloUser() {
		return "Hello User";
	}

	@GetMapping("/admin")
	public String helloAdmin() {
		return "Hello Admin";
	}

}