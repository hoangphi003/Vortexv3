package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Account;
import java.util.List;

import javax.transaction.Transactional;

public interface LoginDao extends JpaRepository<Account, String> {

    @Transactional
    @Query("select a from Account a where a.UserName = :username and a.Password = :password")
    Account findByUserNameAndPassword(@Param("username") String userName, @Param("password") String password);
}