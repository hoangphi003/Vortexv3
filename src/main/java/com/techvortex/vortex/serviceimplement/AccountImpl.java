package com.techvortex.vortex.serviceimplement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.repository.AccountDao;
import com.techvortex.vortex.service.AccountService;

import javax.transaction.Transactional;
@Service
public class AccountImpl implements AccountService {
         
    @Autowired
	AccountDao accountDAO;

    @Override
    public List<Account> findAll() {
        // TODO Auto-generated method stub
        return accountDAO.findAll();
    }

    @Override
    public Account create(Account account) {
        // TODO Auto-generated method stub
        return accountDAO.save(account);
    }


    @Override
    public void delete(Account account) {
        // TODO Auto-generated method stub
        accountDAO.delete(account);
    }

    @Override
    @Transactional
    public Account findById(String UserName) {
        Optional<Account> optionalAccount = accountDAO.findById(UserName);
        return optionalAccount.orElse(null);
    }

    @Override
@Transactional
public void updateStatus(String UserName, Boolean Active) {
    try {
        Account account = findById(UserName);
        if (account != null) {
            account.setActive(Active);
            // Đảm bảo sử dụng save để cập nhật đối tượng
            accountDAO.save(account);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    



    
    


 
}
