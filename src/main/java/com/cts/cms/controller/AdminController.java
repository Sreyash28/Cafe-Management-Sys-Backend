package com.cts.cms.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/admin")
//@RequiredArgsConstructor
public class AdminController {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello";
	}

}