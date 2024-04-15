package com.techvortex.vortex.admincontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.techvortex.vortex.configuration.SecurityConfig;
import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.service.RegisterService;

@Controller
public class LoginController {

    @Autowired
    SecurityConfig config;

    @Autowired
    RegisterService registerService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/auth/login")
    public String HomeLogin(Account account) {
        return "admin/login";
    }

    @PostMapping("/auth/UserLogin")
    public String UserLoginHome() {
        return "admin/login";
    }

    @GetMapping("/auth/login/success")
    public String SuccessLogin(Model model) {
        model.addAttribute("messageSuccess", "Successfully");
        return "redirect:/index";
    }

    @GetMapping("/auth/login/fail")
    public String FailLogin(Model model) {
        model.addAttribute("messageFail", "Invalid username or password");
        return "/admin/login";
    }
}