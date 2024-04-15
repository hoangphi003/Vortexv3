package com.techvortex.vortex.admincontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Brand;
import com.techvortex.vortex.entity.Discount;
import com.techvortex.vortex.service.DiscountService;

@Controller
@RequestMapping("/admin")
public class DiscountController {

    @Autowired
    DiscountService discountService;

    // field du lieu len table
    @GetMapping("/discount")
    public String showDiscountList(@ModelAttribute("discount") Discount discount,BindingResult bindingResult, Model model) {
        model.addAttribute("alldiscount", discountService.findAll());
        model.addAttribute("discount", new Discount());
        return "/admin/pages/Discount";
    }

    @GetMapping("/formdiscount")
    public String FormDiscount(@ModelAttribute("discount") Discount discount,BindingResult bindingResult, Model model) {
        model.addAttribute("alldiscount", discountService.findAll());
        model.addAttribute("discount", new Discount());
        return "/admin/pages/FormDiscount";
    }

    // chuc nang insert
    @PostMapping("/savediscount")
public String createDiscount(@Validated @ModelAttribute("discount") Discount discount, BindingResult bindingResult,
        Model model, RedirectAttributes redirectAttributes) {
    // Kiểm tra xem ngày bắt đầu có nhỏ hơn ngày kết thúc không
    if (discount.getStartDay() != null && discount.getEndDay() != null && discount.getStartDay().after(discount.getEndDay())) {
        bindingResult.addError(new FieldError("discount", "StartDay", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc"));
    }

    if (bindingResult.hasErrors()) {
        // Nếu có lỗi, trả về trang Discount với thông báo lỗi
        model.addAttribute("alldiscount", discountService.findAll());
        return "/admin/pages/FormDiscount";
    }

    // Nếu không có lỗi, thực hiện lưu giảm giá và chuyển hướng đến trang danh sách giảm giá
    discountService.create(discount);
    redirectAttributes.addFlashAttribute("successMessage", "Lưu giảm giá thành công!");
    return "redirect:/admin/formdiscount";
}

    @GetMapping("discount/edit/{discountId}")
    public String showUpdateForm(@PathVariable("discountId") Integer discountId, Model model) {
        // Lấy discount cần sửa
        Optional<Discount> optionalDiscount = discountService.findById(discountId);
        model.addAttribute("discount", optionalDiscount);
        model.addAttribute("alldiscount", discountService.findAll());
        return "/admin/pages/FormDiscount";
    }

    // chuc nang delete
    @GetMapping("discount/delete/{discountId}")
    public String deleteDiscount(@PathVariable("discountId") Integer discountId, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Discount> optionalDiscount = discountService.findById(discountId);

            if (optionalDiscount.isPresent()) {
                Discount discount = optionalDiscount.get();

                discountService.delete(discount);
                // Đặt thông báo thành công vào redirectAttributes
                redirectAttributes.addFlashAttribute("deletesuccessMessage", "Xóa thành công!");
            } else {
                throw new IllegalArgumentException("Invalid discount Id:" + discountId);
            }
        } catch (Exception e) {
            // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
            redirectAttributes.addFlashAttribute("deleteerrorMessage", "Xóa thất bại: " + e.getMessage());
        }
        return "redirect:/admin/discount";
    }
}
