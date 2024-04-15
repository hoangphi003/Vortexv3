package com.techvortex.vortex.admincontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.service.OrderDetailServiceAdmin;
import com.techvortex.vortex.service.OrderServiceAdmin;
import com.techvortex.vortex.service.ProductDetailServiceAdmin;

@Controller
@RequestMapping("/admin")
public class OrderDetailController {
    @Autowired
    private OrderDetailServiceAdmin orderDetailService;

    @Autowired
    OrderServiceAdmin orderService;

    @Autowired
    ProductDetailServiceAdmin productdeService;

    @GetMapping("/orderdetail")
    public String OrderDetail(Model model) {
        model.addAttribute("orderdetail", new OrderDetail());
        model.addAttribute("allOrderDetail", orderDetailService.findAll());
        model.addAttribute("allOrderService", orderService.findAll());
        model.addAttribute("allProduct", productdeService.findAll());
        return "admin/pages/OrderDetail";
    }

   // Thêm @ModelAttribute cho phương thức populateYears() để truyền danh sách năm vào view
// @ModelAttribute("years")
// public List<Integer> populateYears() {
//     // Populate the dropdown with years, e.g., from 2020 to the current year
//     int currentYear = YearMonth.now().getYear();
//     return IntStream.rangeClosed(2022, currentYear)
//             .boxed()
//             .collect(Collectors.toList());
// }

// // Controller method để hiển thị biểu đồ tròn
// @GetMapping("/ordersByYear")
// public String ordersByYear(Model model) {
//     List<Integer> years = populateYears(); // Lấy danh sách năm từ phương thức populateYears()
//     model.addAttribute("years", years);
    
//     // Đoạn mã khác để lấy dữ liệu và hiển thị biểu đồ tròn
//     // ...
//     return "admin/index"; // Thay "your-page" bằng tên trang của bạn
// }
    


    @GetMapping("")
    public String totalSum(Model model, @RequestParam(name = "year", defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int year) {
       
        // Lấy tổng doanh thu và đặt nó vào model để hiển thị trên giao diện người dùng
        Float totalRevenue = orderDetailService.calculateTotalRevenue();
        model.addAttribute("totalRevenue", totalRevenue);

        // Lấy tổng số lượng sản phẩm đã bán và đặt nó vào model để hiển thị trên giao
        // diện người dùng
        Integer totalProductsSold = orderDetailService.calculateTotalProductsSold();
        model.addAttribute("totalProductsSold", totalProductsSold);

        // Lấy số lượng đơn hàng bán được và đặt nó vào model để hiển thị trên giao diện
        // người dùng
        Integer totalOrdersSold = orderDetailService.calculateTotalOrdersSold();
        model.addAttribute("totalOrdersSold", totalOrdersSold);

        int totalRemainingProducts = productdeService.getTotalRemainingProducts();
        model.addAttribute("totalRemainingProducts", totalRemainingProducts);
        // biểu đồ cột
        List<Long> monthlyOrderCounts = orderService.countOrdersByMonthInYear(year);
        System.out.println("Year: " + year);
        model.addAttribute("monthlyOrderCounts", monthlyOrderCounts);

        // tính tổng đơn hàng
        model.addAttribute("totalOrders", orderService.getTotalOrders());

        // biểu đồ tròn
        // Lấy dữ liệu đếm số đơn hàng theo từng năm
        // List<Long> ordersByYear = orderService.countOrdersByYear();

        // // Chuyển đổi dữ liệu thành chuỗi JSON để truyền vào JavaScript
        // String ordersByYearJson = new Gson().toJson(ordersByYear);
        // model.addAttribute("ordersByYearJson", ordersByYearJson);
        // Lấy danh sách các tháng từ 1 đến 12
     // Tạo một danh sách để lưu tổng doanh thu của từng tháng trong năm
    
        return "admin/index";
    }

    // Trong OrderDetailController
@GetMapping("/monthlyRevenue")
public ResponseEntity<?> getMonthlyRevenue(Model model) {
    // Tính toán tổng doanh thu của 12 tháng trong năm hiện tại
    List<Float> monthlyRevenue = orderService.calculateMonthlyRevenueInCurrentYear();

    // Chuyển đổi danh sách thành chuỗi JSON
    String monthlyRevenueJson = new Gson().toJson(monthlyRevenue);

    // Trả về chuỗi JSON
    return ResponseEntity.ok(monthlyRevenueJson);
}


}
