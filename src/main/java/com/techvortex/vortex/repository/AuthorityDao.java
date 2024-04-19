package com.techvortex.vortex.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {
    @Query("SELECT DISTINCT ar FROM Authority ar WHERE ar.account IN ?1")
    List<Authority> authoritiesOf(List<Account> accounts);

    @Transactional
    @Modifying
    @Query("DELETE FROM Authority a WHERE a.AuthorityID = :authorityId")
    void deleteByAuthorityID(@Param("authorityId") Integer authorityId);
}
