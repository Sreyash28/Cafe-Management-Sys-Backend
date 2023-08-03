package com.cts.cms;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.cms.model.RoleName;
import com.cts.cms.model.Users;

@SpringBootApplication
public class CafeManagementSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeManagementSysApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(com.cts.cms.service.UserService userService,
//			com.cts.cms.repository.RoleRepository roleRepository, com.cts.cms.repository.UserRepository userRepository,
//			com.cts.cms.repository.MenuRepository menuRepository,
//			com.cts.cms.repository.OrdersRepository ordersRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			userService.saveRole(new com.cts.cms.model.Role(RoleName.USER));
//			userService.saveRole(new com.cts.cms.model.Role(RoleName.ADMIN));
//			userService.createUser(
//					new Users("ssp@gmail.com", passwordEncoder.encode("sspPassword"), new ArrayList<>()));
//			
//
//			com.cts.cms.model.Role role = roleRepository.findByRoleName(RoleName.ADMIN);
//			Users user = userRepository.findByEmail("ssp@gmail.com").orElse(null);
//			user.getRoles().add(role);
//			userService.createUser(user);
//
//		};
//	}

}
