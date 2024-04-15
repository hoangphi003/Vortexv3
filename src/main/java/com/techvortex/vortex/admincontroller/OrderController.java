package com.techvortex.vortex.admincontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.service.AccountService;
import com.techvortex.vortex.service.DiscountService;
import com.techvortex.vortex.service.OrderService;
import com.techvortex.vortex.service.OrderServiceAdmin;

@Controller
@RequestMapping("/admin")
public class OrderController {

    @Autowired
    OrderServiceAdmin orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    DiscountService discountService;

    @GetMapping("/order")
    public String Order(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("allOrder", orderService.findAll());
        model.addAttribute("allAccount", accountService.findAll());
        model.addAttribute("allDiscount", discountService.findAll());

        // Thêm code để tính toán số đơn hàng chờ thanh toán, vận chuyển, đang giao
        // hàng, hoàn thành và đã hủy
        long pendingPaymentOrders = orderService.countPendingPaymentOrders();
        long shippingOrders = orderService.countShippingOrders();
        long deliveringOrders = orderService.countDeliveringOrders();
        long completedOrders = orderService.countCompletedOrders();
        long cancelledOrders = orderService.countCancelledOrders();

        // Đặt giá trị vào model để hiển thị trong trang HTML
        model.addAttribute("pendingPaymentOrders", pendingPaymentOrders);
        model.addAttribute("shippingOrders", shippingOrders);
        model.addAttribute("deliveringOrders", deliveringOrders);
        model.addAttribute("completedOrders", completedOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);

        // tổng đơn hàng
        model.addAttribute("totalOrders", orderService.getTotalOrders());

        return "admin/pages/Order";
    }

    @GetMapping("/orderform")
    public String OrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("allOrder", orderService.findAll());
        return "admin/pages/OrderForm";
    }

    @GetMapping("/editorder/{OrderId}")
    public String showUpdateForm(@PathVariable("OrderId") Integer OrderId, Model model) {
        // Lấy order cần sửa
        Order order = orderService.findById(OrderId);
        // Kiểm tra xem order có tồn tại hay không
        if (order == null) {
            // Xử lý tùy thuộc vào yêu cầu của ứng dụng khi order không tồn tại
            return "redirect:/admin/order"; // Chuyển hướng đến trang danh sách orders
        }
        model.addAttribute("order", order);
        model.addAttribute("allOrder", orderService.findAll());
        // Trả về fragment của form
        return "admin/pages/OrderForm";
    }

    // @PostMapping("/saveOrder")
    // public String saveorder(@ModelAttribute("order") Order order,
    // RedirectAttributes redirectAttributes) {
    // orderService.create(order);
    // redirectAttributes.addFlashAttribute("order", order);
    // return "redirect:/admin/order";
    // }

    @PostMapping("/updateOrderStatus")
    public ResponseEntity<String> updateOrderStatus(@RequestParam Integer orderId, @RequestParam String newStatus) {
        
        return orderService.updateOrderStatus(orderId, newStatus);
    }

    // edit order sang order detail
    // @GetMapping("/editorderdetai/{orderId}")
    // public String editProductdetail(@PathVariable("orderId") Integer orderId,
    // Model model) {
    // Order order = orderService.findById(orderId);

    // if (order == null) {
    // model.addAttribute("errorMessage", "Không tìm thấy thông tin đơn hàng");
    // return "redirect:/admin/order";
    // }

    // model.addAttribute("order", order);
    // model.addAttribute("allOrder", orderService.findAll());
    // model.addAttribute("orderDetail", new OrderDetail());
    // return "admin/pages/OrderDetail";
    // }

}
