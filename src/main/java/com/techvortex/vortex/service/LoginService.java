package com.techvortex.vortex.service;

import com.techvortex.vortex.entity.Account;

public interface LoginService {

    Account findById(String username);

    Account findByUserNameAndPassword(String userName, String password);

    Account save(Account account);
}
