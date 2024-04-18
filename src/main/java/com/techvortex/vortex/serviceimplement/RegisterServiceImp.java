package com.techvortex.vortex.serviceimplement;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.Authority;
import com.techvortex.vortex.entity.Role;
import com.techvortex.vortex.repository.AuthorityDao;
import com.techvortex.vortex.repository.RegisterDao;
import com.techvortex.vortex.repository.RoleDao;
import com.techvortex.vortex.service.RegisterService;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    RegisterDao registerDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    AuthorityDao authorityDao;

    @Override
    public void save(Account account) {
        account.setActive(true);
        registerDao.save(account);

        Role role = roleDao.findByRoleId("Customer");

        if (role == null) {
            role = new Role();
            role.setRoleId("Customer");
        }

        Authority authority = new Authority();
        authority.setRole(role);
        authority.setAccount(account);

        authorityDao.save(authority);
    }

    @Override
    public List<Account> CheckEmailUser(String email) {
        return registerDao.CheckEmail(email);
    }

    @Override
    public Account CheckUserName(String username) {
        return registerDao.CheckUserName(username);
    }

    @Override
    public Account findByUserNameG(String userName) {
        return registerDao.findByUserNameG(userName);
    }

    @Override
    public Account insertUserByPhoneNumber(String phone) {
        return registerDao.insertUserByPhoneNumber(phone);
    }
}
