package com.techvortex.vortex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvortex.vortex.entity.Account;

public interface DoiMatKhauDao extends JpaRepository<Account,String>{
    
    // Đổi mật khẩu Nam
    @Query("SELECT a FROM Account a WHERE a.UserName = :username")
    Account findByUsername(@Param("username") String username);
}
