package com.techvortex.vortex.restcontroller;

import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.Authority;
import com.techvortex.vortex.service.LoginService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class LoginRestController {

    @Autowired
    LoginService loginService;

    @PostMapping("/loginform")
    public ResponseEntity<Account> LoginController(@RequestBody Account account) {
        String userName = account.getUserName();
        String password = account.getPassword();
        Account user = loginService.findByUserNameAndPassword(userName, password);
        if (user.getActive() == false) {
            return ResponseEntity.notFound().build();
        }
        // for (Authority x : user.getAuthorities()) {
        // if (x.getRole().equals("Admin")) {
        // System.out.println("Đây là role của nó: " + x.getRole());
        // }
        // }
        return ResponseEntity.ok(user);
    }

  

}
