package com.techvortex.vortex.admincontroller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.techvortex.vortex.configuration.sendMailByCancelOrderAdmin;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.service.AccountService;
import com.techvortex.vortex.service.DiscountService;
import com.techvortex.vortex.service.OrderDetailServiceAdmin;
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
    sendMailByCancelOrderAdmin mailByCancelOrderAdmin;

    @Autowired
    OrderDetailServiceAdmin orderdetailService;

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

    @GetMapping("/orderdetail/{orderId}")
    public String showOrderDetail(@PathVariable("orderId") Integer orderId, Model model) {
        Order order = orderService.findById(orderId);

        if (order == null) {
            model.addAttribute("errorMessage", "Không tìm thấy thông tin đơn hàng");
            return "redirect:/admin/order";
        }

        model.addAttribute("order", order);
        // Lấy danh sách các mục trong đơn hàng chi tiết của đơn hàng này
        List<OrderDetail> orderDetails = order.getOrderDetails();
        model.addAttribute("allOrderDetail", orderDetails);

        // Tính tổng số tiền của đơn hàng
        float totalAmount = 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalAmount += orderDetail.getTotal();
        }
        model.addAttribute("totalAmount", totalAmount);

        return "admin/pages/OrderDetail";
    }

    @GetMapping("/orderdate")
    public String getOrderDate(Model model,
            @RequestParam(value = "selectedYear", required = false) Integer selectedYear,
            @RequestParam(value = "selectedMonth", required = false) Integer selectedMonth,
            @RequestParam(value = "selectedDay", required = false) Integer selectedDay) {
        // Lấy danh sách các năm từ cơ sở dữ liệu
        List<Integer> distinctYears = orderService.findDistinctYears();
        model.addAttribute("distinctYears", distinctYears);

        // Thêm giá trị mặc định của năm (năm hiện tại) vào model nếu không được chọn
        if (selectedYear == null) {
            selectedYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        model.addAttribute("selectedYear", selectedYear);
        // Truyền các giá trị tháng và ngày vào model nếu có
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("selectedDay", selectedDay);

        // Lấy danh sách đơn hàng nếu có
        if (selectedYear != null && selectedMonth != null && selectedDay != null) {
            List<Order> orders = orderService.findOrdersByDate(selectedDay, selectedMonth, selectedYear);
            model.addAttribute("orders", orders);
        }

        return "admin/pages/OrderReport";
    }

    @GetMapping("/orderdate/result")
    public String getOrderDateResult(
            @RequestParam("selectedYear") Integer selectedYear,
            @RequestParam("selectedMonth") Integer selectedMonth,
            @RequestParam("selectedDay") Integer selectedDay,
            RedirectAttributes redirectAttributes) {
        // Lấy danh sách đơn hàng từ service dựa trên ngày, tháng và năm đã chọn
        List<Order> orders = orderService.findOrdersByDate(selectedDay, selectedMonth, selectedYear);

        // Truyền lại các giá trị đã chọn vào form bằng cách sử dụng RedirectAttributes
        redirectAttributes.addAttribute("selectedYear", selectedYear);
        redirectAttributes.addAttribute("selectedMonth", selectedMonth);
        redirectAttributes.addAttribute("selectedDay", selectedDay);

        // Redirect về trang "orderdate" thay vì "ordertotal"
        return "redirect:/admin/orderdate";
    }

    @GetMapping("/sendmailbyadmin")
    public ResponseEntity<String> sendMailByAdmin(@RequestParam Integer orderId, @RequestParam String reasonOrder) {
        Order useOrder = orderService.findOrderById(orderId);
        String email = useOrder.getAccount().getEmail();
        String fullName = useOrder.getAccount().getFullName();
        Date date = useOrder.getOrderDate();
        String stringGender = "";
        Boolean gender = useOrder.getAccount().getGender();

        if (gender == null) {
            stringGender = "Quý khách";
        } else {
            if (gender) {
                stringGender = "Anh";
            } else {
                stringGender = "Chị";
            }
        }

        mailByCancelOrderAdmin.sendEmailCancelOrder(email, fullName, date, stringGender, reasonOrder);

        return ResponseEntity.ok().build();
    }

}
