package com.techvortex.vortex.admincontroller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Order;
import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.service.AccountService;
import com.techvortex.vortex.service.DiscountService;
import com.techvortex.vortex.service.OrderDetailServiceAdmin;
import com.techvortex.vortex.service.OrderService;
import com.techvortex.vortex.service.OrderServiceAdmin;

import lombok.Getter;

@Controller
@RequestMapping("/admin")
public class Revenue {

    @Autowired
    OrderServiceAdmin orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    OrderDetailServiceAdmin orderdetailService;

    @Autowired
    DiscountService discountService;

    @GetMapping("/revenue")
    public String getRevenue(Model model,OrderDetail orderdetails,
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
            model.addAttribute("allorderdetail", orderdetailService.findAll());
        }

        return "admin/pages/revenue";
    }

    @GetMapping("/showrevenue")
    public String ShowRevenue() {
        return "admin/pages/RevenueShow";
    }

        
    @GetMapping("/revenue/result")
    public String getRevenueResult(
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
        return "redirect:/admin/revenue";
    }

}
