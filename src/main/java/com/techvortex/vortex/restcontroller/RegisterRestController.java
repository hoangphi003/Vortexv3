package com.techvortex.vortex.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.service.LoginService;
import com.techvortex.vortex.service.RegisterService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class RegisterRestController {

    @Autowired
    RegisterService registerService;

    @Autowired
    LoginService loginService;

    @PostMapping("/registerform")
    public ResponseEntity<Account> postRegisterForm(@RequestBody Account account) {
        Account user = registerService.CheckUserName(account.getUserName());
        if (user != null) {
            if (user.getUserName().equals(account.getUserName())) {
                return ResponseEntity.notFound().build();
            }
        } else {
            Account newUser = new Account();
            newUser.setUserName(account.getUserName());
            newUser.setPassword(account.getPassword());
            newUser.setPassword(account.getEmail());
            newUser.setActive(true);
            newUser.setAvatar("anonymous.png");
            registerService.save(newUser);
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/facebookform")
    public ResponseEntity<Account> postFbForm(@RequestBody Account account) {
        Account user = registerService.CheckUserName(account.getUserName());

        if (user == null) {
            Account newAccount = new Account();
            newAccount.setUserName(account.getUserName());
            newAccount.setActive(true);
            newAccount.setAvatar("anonymous.png");
            registerService.save(newAccount);
            return ResponseEntity.ok(newAccount);
        }

        {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping("/googleform")
    public ResponseEntity<Account> postGoogleForm(@RequestBody Account account) {
        Account user = registerService.CheckUserName(account.getUserName());

        if (user == null) {
            Account newAccount = new Account();
            newAccount.setUserName(account.getUserName());
            newAccount.setActive(true);
            newAccount.setEmail(account.getEmail());
            newAccount.setAvatar("anonymous.png");
            newAccount.setFullName(account.getFullName());
            registerService.save(newAccount);
            return ResponseEntity.ok(newAccount);
        }

        {
            return ResponseEntity.ok(user);
        }
    }

    @PostMapping("/phonenumberform/{phone}")
    public ResponseEntity<Account> LoginByPhoneNumber(@PathVariable("phone") String phone) {
        Account user = registerService.insertUserByPhoneNumber(phone);
        if (user == null) {
            Account newAccount = new Account();
            newAccount.setUserName(phone);
            newAccount.setAvatar("anonymous.png");
            newAccount.setActive(true);
            newAccount.setPhone(phone);
            registerService.save(newAccount);
            return ResponseEntity.ok(newAccount);
        }
        {
            return ResponseEntity.ok(user);
        }
    }

}
