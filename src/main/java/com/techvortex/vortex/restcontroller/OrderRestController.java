package com.techvortex.vortex.restcontroller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.configuration.sendMailByCancelOrder;
import com.techvortex.vortex.entity.CancelOrderForm;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.entity.ProductDetail;
import com.techvortex.vortex.service.OrderDetailService;
import com.techvortex.vortex.service.OrderService;
import com.techvortex.vortex.service.ProductDetailService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @Autowired
    sendMailByCancelOrder mailByCancelOrder;

    @Autowired
    ProductDetailService detailService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/vieworder/{username}")
    public ResponseEntity<List<Order>> getViewOrder(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderService.findOrderByUsername(username));
    }

    @GetMapping("/viewawaitingpayment/{username}")
    public ResponseEntity<List<Order>> getViewAwaitingPayment(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderService.findOrderByStatus(username, "%Chờ xác nhận%"));
    }

    @GetMapping("/viewawaitingconfirm/{username}")
    public ResponseEntity<List<Order>> getViewAwaitingConfirm(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderService.findOrderByStatus(username, "%Chờ vận chuyển%"));
    }

    @GetMapping("/viewintransit/{username}")
    public ResponseEntity<List<Order>> getViewInTransit(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderService.findOrderByStatus(username, "%Đã xác nhận%"));
    }

    @GetMapping("/waitingfordelivery/{username}")
    public ResponseEntity<List<Order>> getWaitingForDelivery(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderService.findOrderByStatus(username, "%Đang giao hàng%"));
    }

    @GetMapping("/complete/{username}")
    public ResponseEntity<List<Order>> getComplete(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderService.findOrderByStatus(username, "%Hoàn thành%"));
    }

    @GetMapping("/cancel/{username}")
    public ResponseEntity<List<Order>> getCancel(@PathVariable("username") String username) {
        return ResponseEntity.ok(orderService.findOrderByStatus(username, "%Đã hủy%"));
    }

    @GetMapping("/cancelorder/{id}")
    public ResponseEntity<List<Order>> cancelOrder(@PathVariable("id") Integer id) {
        Order useOrder = orderService.findOrderById(id);

        useOrder.setOrderStatus("Đã hủy");
        orderService.save(useOrder);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendmailcancelorder/{id}")
    public ResponseEntity<List<Order>> sendMailCancelOrder(@PathVariable("id") Integer id,
            @RequestBody CancelOrderForm form)
            throws UnsupportedEncodingException {
        Order useOrder = orderService.findOrderById(id);
        String email = useOrder.getAccount().getEmail();
        String fullName = useOrder.getAccount().getFullName();
        Date date = useOrder.getOrderDate();
        String stringGender = "";
        Boolean gender = useOrder.getAccount().getGender();
        String reason = form.getReason();

        if (gender == null) {
            stringGender = "Quý khách";
        } else {
            if (gender) {
                stringGender = "Anh";
            } else {
                stringGender = "Chị";
            }
        }

        if (useOrder.getPaymentMethod().equalsIgnoreCase("payPal")
                || useOrder.getPaymentMethod().equalsIgnoreCase("vnPay")) {

            if (useOrder.getAccount().getEmail() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            mailByCancelOrder.sendEmailCancelOrder(email, fullName, date, stringGender, reason);

        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getordersuccess/{id}")
    public ResponseEntity<List<Order>> getOrderSuccess(@PathVariable("id") Integer id) {
        Order useOrder = orderService.findOrderById(id);
        useOrder.setOrderStatus("Hoàn thành");
        orderService.save(useOrder);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviewed/{id}")
    public ResponseEntity<List<Order>> reviewed(@PathVariable("id") Integer id) {
        Order useOrder = orderService.findOrderById(id);
        useOrder.setOrderStatus("Đánh giá");
        orderService.save(useOrder);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/updateinventory/{orderid}")
    public ResponseEntity<Order> UpdateInventory(@PathVariable("orderid") Integer orderid) {
        List<OrderDetail> findOrder = orderService.findOrderDetailByOrderId(orderid);

        for (OrderDetail x : findOrder) {
            for (ProductDetail item : x.getProduct().getProductDetails()) {
                ProductDetail detail = detailService.findById(item.getProductDetailId());
                if (x.getQuantity() < detail.getInventoryQuantity()) {
                    detail.setInventoryQuantity(detail.getInventoryQuantity() - x.getQuantity());
                    detailService.save(detail);
                }

            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getordered/{id}")
    public ResponseEntity<List<Order>> getOrdered(@PathVariable("id") Integer id) {
        Order useOrder = orderService.findOrderById(id);
        useOrder.setOrderStatus("Đánh giá");
        orderService.save(useOrder);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getorderdetailsetstatus/{id}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailSetStatus(@PathVariable("id") Integer id) {
        List<OrderDetail> useOrder = orderService.findOrderDetailByOrderId(id);
        Order order = orderService.findOrderById(id);

        boolean anyNotReviewed = false;
        for (OrderDetail x : useOrder) {
            if (!x.getReviewed()) {
                anyNotReviewed = true;
                break;
            }
        }

        if (!anyNotReviewed) {
            order.setOrderStatus("Hoàn thành");
            orderService.save(order);
        }
        return ResponseEntity.ok(useOrder);
    }

}