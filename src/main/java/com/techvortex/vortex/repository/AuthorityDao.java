package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techvortex.vortex.entity.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {

}
