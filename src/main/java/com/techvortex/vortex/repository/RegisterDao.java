package com.techvortex.vortex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Account;

public interface RegisterDao extends JpaRepository<Account, String> {

    @Query("select a from Account a where a.Email = :e")
    List<Account> CheckEmail(@Param("e") String email);

    @Query("select a from Account a where a.UserName = :u")
    Account CheckUserName(@Param("u") String UserName);

    @Query("select a from Account a where a.UserName = :name")
    Account findByUserNameG(@Param("name") String userName);

}
