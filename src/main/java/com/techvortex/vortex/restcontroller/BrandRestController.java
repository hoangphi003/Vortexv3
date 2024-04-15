package com.techvortex.vortex.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.service.BrandService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
public class BrandRestController {

    @Autowired
    BrandService brandService;

    @GetMapping("/findAllBrand")
    public ResponseEntity<List<Brand>> getMethodName() {
        return ResponseEntity.ok(brandService.findAll());
    }

}
