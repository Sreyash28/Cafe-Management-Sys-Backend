			package com.cts.cms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.cms.dto.BearerToken;
import com.cts.cms.dto.LoginDto;
import com.cts.cms.dto.LoginResponseDto;
import com.cts.cms.dto.RegisterDto;
import com.cts.cms.exception.AuthorizationException;
import com.cts.cms.exception.UserNotFoundException;
import com.cts.cms.jwt.security.JwtUtilities;
import com.cts.cms.model.Role;
import com.cts.cms.model.RoleName;
import com.cts.cms.model.Users;
import com.cts.cms.repository.RoleRepository;
import com.cts.cms.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final AuthenticationManager authenticationManager;
	private final UserRepository repository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtilities jwtUtilities;

	@Override
	public Users createUser(Users user) {
		logger.info("Creating user: {}", user);
		return repository.save(user);
	}

	@Override
	public ResponseEntity<?> register(RegisterDto registerDto) {
		if (repository.existsByEmail(registerDto.getEmail())) {
			return new ResponseEntity<>("email is already taken !", HttpStatus.SEE_OTHER);
		} else {
			Users user = new Users();
			user.setEmail(registerDto.getEmail());
			user.setUserName(registerDto.getUserName());
			user.setContactNumber(registerDto.getContactNumber());
			user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
			// By Default , he/she is a simple user
			Role role = roleRepository.findByRoleName(RoleName.USER);
			user.setRoles(Collections.singletonList(role));
			repository.save(user);
			String token = jwtUtilities.generateToken(registerDto.getEmail(),
					Collections.singletonList(role.getRoleName()));
			return new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK);

		}
	}

	@Override
	public LoginResponseDto authenticate(LoginDto loginDto) throws AuthorizationException {
		log.info("Start");
		LoginResponseDto responseDto = null;
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("getName(): {}", authentication.getName());
			Users user = repository.findByEmail(authentication.getName())
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			List<String> rolesNames = new ArrayList<>();
			user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));
			String token = jwtUtilities.generateToken(user.getUsername(), rolesNames);
			responseDto = new LoginResponseDto();
			responseDto.setToken(token);

		} catch (BadCredentialsException e) {
			log.error("Bad Credentials");
			throw new AuthorizationException(e.getMessage());
		} catch (Exception e) {
			log.error("Bad Credentials");
			throw new AuthorizationException(e.getMessage());
		}
		log.info("End");
		return responseDto;
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public List<Users> getAllUsers() {
		logger.info("Getting all users");
		return repository.findAll();
	}

	@Override
	public Users findUserById(int id) throws UserNotFoundException {
		logger.info("Finding user by ID: {}", id);
		Optional<Users> optional = repository.findById(id);
		if (optional.isEmpty()) {
			logger.warn("User not found with ID: {}", id);
			throw new UserNotFoundException("User not found with ID: " + id);
		}
		Users user = optional.get();
		logger.info("Found user: {}", user);
		return user;
	}

	@Override
	public void deleteById(int id) throws UserNotFoundException {
		logger.info("Deleting user by ID: {}", id);
		Users user = findUserById(id);
		logger.info("Deleted user with ID: {}", id);
		repository.delete(user);
	}

	@Override
	public Users update(int id, Users newuser) throws UserNotFoundException {
		logger.info("Updating user with ID: {}, new user data: {}", id, newuser);
		Users user = findUserById(id);
		user.setEmail(newuser.getEmail());
		user.setUserName(newuser.getUsername());
		user.setContactNumber(newuser.getContactNumber());
		user.setContactNumber(newuser.getPassword());
		user.setPassword(passwordEncoder.encode(newuser.getPassword()));
		user.setRoles(newuser.getRoles());
		return repository.save(user);
	}

}
