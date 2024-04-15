package com.techvortex.vortex.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.entity.Discount;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.entity.OrderDiscount;
import com.techvortex.vortex.entity.Product;
import com.techvortex.vortex.service.CheckoutService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
public class CheckoutRestController {

    @Autowired
    CheckoutService checkoutService;

    @PostMapping("/addorder/")
    public ResponseEntity<Order> addorder(@RequestBody Order order) {

        checkoutService.save(order);

        for (OrderDetail x : order.getOrderDetails()) {

            OrderDetail orderDetail = new OrderDetail();
            Product product = new Product();

            product.setProductId(x.getProduct().getProductId());
            orderDetail.setPrice(x.getPrice());
            orderDetail.setQuantity(x.getQuantity());
            orderDetail.setTotal(x.getQuantity() * x.getPrice());
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setReviewed(false);

            checkoutService.saveOrderDetail(orderDetail);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addorderdiscount/{discountid}")
    public ResponseEntity<Order> postMethodName(@RequestBody Order order,
            @PathVariable("discountid") Integer discountid) {

        checkoutService.save(order);

        for (OrderDetail x : order.getOrderDetails()) {

            OrderDetail orderDetail = new OrderDetail();
            Product product = new Product();
            OrderDiscount orderDiscount = new OrderDiscount();
            Discount discount = new Discount();

            discount.setDiscountId(discountid);

            product.setProductId(x.getProduct().getProductId());
            orderDetail.setPrice(x.getPrice());
            orderDetail.setQuantity(x.getQuantity());
            orderDetail.setTotal(x.getQuantity() * x.getPrice());
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setReviewed(false);

            orderDiscount.setDiscount(discount);
            orderDiscount.setOrder(order);

            checkoutService.saveOrderDetail(orderDetail);
            checkoutService.saveOrderDiscount(orderDiscount);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getdiscount/{code}")
    public ResponseEntity<Discount> getDiscount(@PathVariable("code") String code) {
        return ResponseEntity.ok(checkoutService.getDiscountCode(code));
    }

    @GetMapping("/setqtydiscount/{id}")
    public ResponseEntity<Discount> setQtyDiscount(@PathVariable("id") Integer id) {
        Discount discount = checkoutService.findDiscountId(id);

        discount.setQuantity(discount.getQuantity() - 1);
        checkoutService.saveDiscount(discount);
        return ResponseEntity.ok().build();
    }

}
