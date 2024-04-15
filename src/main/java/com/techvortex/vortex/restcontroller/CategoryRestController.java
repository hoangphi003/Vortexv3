package com.techvortex.vortex.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Category;
import com.techvortex.vortex.service.CategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/findAllCategory")
    public ResponseEntity<List<Category>> getCategory() {
        return ResponseEntity.ok(categoryService.findAllDesc());
    }

}
