package com.techvortex.vortex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techvortex.vortex.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, String> {
    @Query("SELECT a FROM Account a JOIN a.authorities auth JOIN auth.role r WHERE r.RoleId = 'Staff'")
    List<Account> findStaffAccounts();

    @Query("SELECT a FROM Account a JOIN a.authorities auth JOIN auth.role r WHERE r.RoleId = 'Customer'")
    List<Account> findAllCustomerAccounts();

    @Query("SELECT DISTINCT a FROM Authority ar" +
            " inner join ar.account a" +
            " WHERE ar.role.RoleId IN ('Customer','Staff')")
    List<Account> getAdministrators();
}
