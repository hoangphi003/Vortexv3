package com.techvortex.vortex.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.repository.ForgotPasswordDao;
import com.techvortex.vortex.service.ForgotPasswordService;

@Service
public class ForgotPasswordServiceImp  implements ForgotPasswordService {

    @Autowired
    ForgotPasswordDao forgotDao;

    @Override
    public Account findByEmail(String email) {
        return forgotDao.findByEmail(email);
    }

    @Override
    public void updatePasswordByEmail(String email, String newPassword) {
        forgotDao.updatePasswordByEmail(email, newPassword);
    }
    
}
