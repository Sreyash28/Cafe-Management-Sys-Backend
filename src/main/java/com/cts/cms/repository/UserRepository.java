package com.cts.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.cms.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	boolean existsByEmail(String email);

	Optional<Users> findByEmail(String email);
}
