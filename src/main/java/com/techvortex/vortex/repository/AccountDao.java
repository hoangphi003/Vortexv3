package com.techvortex.vortex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.techvortex.vortex.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, String> {

}
