package com.techvortex.vortex.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Cart;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.service.CartService;
import com.techvortex.vortex.service.OrderService;
import com.techvortex.vortex.service.ProductDetailService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
public class ProductDetailRestController {

    @Autowired
    ProductDetailService detailService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductDetail> getDetail(@PathVariable("id") Integer id) {
        ProductDetail detail = detailService.findByIdUser(id);
        return ResponseEntity.ok(detail);
    }

    @PostMapping("/addtocart")
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
        ProductDetail detail = detailService.findByIdUser(cart.getProductDetails().getProductDetailId());
        Integer detailId = detail.getProductDetailId();
        Integer inventoryQty = detail.getInventoryQuantity();
        String userName = cart.getAccount().getUserName();
        Cart existCart = cartService.findByProductDetailIdCartAndUserName(detailId, userName);

        try {
            if (existCart != null) {
                existCart.setQuantity(cart.getQuantity() + existCart.getQuantity());
                cartService.save(existCart);
            } else {
                cartService.save(cart);
            }
            if (existCart.getQuantity() > inventoryQty) {
                existCart.setQuantity(inventoryQty);
                cartService.save(existCart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(cart);
    }
}