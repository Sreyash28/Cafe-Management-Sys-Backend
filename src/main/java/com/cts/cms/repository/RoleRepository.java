package com.cts.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.cms.model.Role;
import com.cts.cms.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRoleName(RoleName roleName);
}
