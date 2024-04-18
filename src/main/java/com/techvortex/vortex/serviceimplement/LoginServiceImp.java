package com.techvortex.vortex.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.repository.LoginDao;
import com.techvortex.vortex.service.LoginService;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    LoginDao loginDao;

    @Override
    public Account findByUserNameAndPassword(String userName, String password) {
        return loginDao.findByUserNameAndPassword(userName, password);
    }

    @Override
    public Account findById(String username) {
        return loginDao.findById(username).get();
    }

    @Override
    public Account save(Account account) {
        return loginDao.save(account);
    }

    @Override
    public List<Account> getAllPhonNumber(String phone) {
         return loginDao.getAllPhoneNumber(phone);
    }
}
