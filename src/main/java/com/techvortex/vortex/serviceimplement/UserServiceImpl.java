package com.techvortex.vortex.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.repository.AccountDao;
import com.techvortex.vortex.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    AccountDao userRepository;

    @Override
    public Account findById(String username) {
        return userRepository.findById(username).get();
    }

    @Override
    public Account save(Account account) {
        return userRepository.save(account);
    }
}
