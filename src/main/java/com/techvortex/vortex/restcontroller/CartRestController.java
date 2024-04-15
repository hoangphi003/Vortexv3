package com.techvortex.vortex.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

 import com.techvortex.vortex.entity.Cart;
import com.techvortex.vortex.service.CartService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin("*")
@RestController
public class CartRestController {

    @Autowired
    CartService cartService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/findAllCart/{username}")
    public ResponseEntity<List<Cart>> GetAll(@PathVariable("username") String username) {
        Float total = 0f;
        for (Cart x : cartService.findAll(username)) {
            total += x.getQuantity() * x.getProductDetails().getProduct().getPrice();
        }
        return ResponseEntity.ok(cartService.findAll(username));
    }

    @GetMapping("/deleteItemByIdFromCart/{id}")
    public void deleteItemByIdFromCart(@PathVariable("id") Integer id) {
        cartService.deleteCartById(id);
    }

    @GetMapping("/qty/{id}/{quantity}")
    public void IncreaseQuantity(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity) {
        Cart cart = cartService.findById(id);
        cart.setQuantity(quantity);
        cartService.save(cart);
    }

    @GetMapping("/deleteAllById/{username}")
    public void deleteAllById(@PathVariable("username") String username) {
        cartService.deleteAllCartById(username);
    }

    @GetMapping("/countcart/{username}")
    public ResponseEntity<Integer> getCountCart(@PathVariable("username") String username) {
        return ResponseEntity.ok(cartService.countCart(username));
    }

}