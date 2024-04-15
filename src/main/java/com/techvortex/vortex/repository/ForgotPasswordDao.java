package com.techvortex.vortex.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Account;

public interface ForgotPasswordDao extends JpaRepository<Account, String>{
    @Transactional
    @Query("select a from Account a where a.Email = :email")
    Account findByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.Password = :password WHERE a.Email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

}
