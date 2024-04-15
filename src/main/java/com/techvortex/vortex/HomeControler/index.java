package com.techvortex.vortex.HomeControler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class index {
    @GetMapping({ "/", "", "/index", "/home" })
    public String index(Model model) {
        return "/userthymeleaf/index.html";
    }
}