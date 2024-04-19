package com.techvortex.vortex.restcontroller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Role;
import com.techvortex.vortex.service.RoleService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/roles")
public class RoleRestController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public List<Role> getAll() {
        try {
            
            return roleService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            // Không gửi phản hồi sau khi gặp lỗi
            throw new RuntimeException("Error retrieving roles", e); // Throwing a new RuntimeException
        }
    }
}
