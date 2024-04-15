package com.techvortex.vortex.admincontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techvortex.vortex.entity.OrderDetail;
import com.techvortex.vortex.service.OrderDetailServiceAdmin;
import com.techvortex.vortex.service.OrderService;
import com.techvortex.vortex.service.ProductDetailService;
 
@Controller
@RequestMapping("/admin")
public class ReportController {
    @Autowired
    private OrderDetailServiceAdmin orderDetailService;

    @GetMapping("/sanphambanchay")
    public String OrderDetail(Model model) {
        return "admin/SanPhamBanChay";
    }

    
}
