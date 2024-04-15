package com.techvortex.vortex.service;

import com.techvortex.vortex.entity.Account;

public interface ForgotPasswordService {
    
    Account findByEmail(String email);

    void updatePasswordByEmail(String email, String newPassword);

}
