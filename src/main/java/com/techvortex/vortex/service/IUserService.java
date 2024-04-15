package com.techvortex.vortex.service;

import com.techvortex.vortex.entity.Account;

public interface IUserService {
    Account findById(String username);

    Account save(Account account);
}
