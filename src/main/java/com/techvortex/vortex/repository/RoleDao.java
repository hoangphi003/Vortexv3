package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Role;

public interface RoleDao extends JpaRepository<Role, String> {

    @Query("SELECT r FROM Role r WHERE r.RoleId = :roleId")
    Role findByRoleId(@Param("roleId") String roleId);
}
