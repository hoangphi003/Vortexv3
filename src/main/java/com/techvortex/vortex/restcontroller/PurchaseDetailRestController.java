package com.techvortex.vortex.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.service.PurchaseDetailService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin("*")
@RestController
public class PurchaseDetailRestController {

    @Autowired
    PurchaseDetailService purchaseService;

    @GetMapping("/findOrderDetail/{username}/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        Order orderUser = purchaseService.findByOrderIdbyUserName(username, id);
        return ResponseEntity.ok(orderUser);
    }

}
