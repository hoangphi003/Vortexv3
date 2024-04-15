package com.techvortex.vortex.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.service.MaterialService;

@Controller
@RequestMapping("/admin")
public class MaterialController {

    @Autowired
    MaterialService materialService;

    @GetMapping("/material")
    public String ListMaterial(@ModelAttribute("material") Material material, Model model) {
        model.addAttribute("material", new Material());
        model.addAttribute("allMaterial", materialService.findAll());
        return "admin/pages/Material";
    }

    @GetMapping("/formmaterial")
    public String FormMaterial(@ModelAttribute("material") Material material, Model model) {
        model.addAttribute("material", new Material());
        return "admin/pages/FormMaterial";
    }

    @PostMapping("/saveMaterial")
    public String saveMaterial(@Validated @ModelAttribute("material") Material material,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        
        // if (materialService.existsByNameIgnoreCase(material.getMaterialName())) {
        // bindingResult.rejectValue("MaterialName", "error.material", "Tên chất liệu đã tồn tại.");
        //  }
        if (materialService.existsByNameIgnoreCase(material.getMaterialName())) {
            bindingResult.rejectValue("materialName", "error.material", "Tên chất liệu đã tồn tại.");
            model.addAttribute("errorMessage", bindingResult.getFieldError("materialName").getDefaultMessage());
            model.addAttribute("allMaterial", materialService.findAll());
            return "admin/pages/FormMaterial";
        }
    
        // if (bindingResult.hasErrors()) {
        //     model.addAttribute("allMaterial", materialService.findAll());
        //     return "admin/pages/Material";
        // }
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessagee", "Chất liệu không được bỏ trống");
            model.addAttribute("allMaterial", materialService.findAll());
            return "admin/pages/FormMaterial";
        }
        
        materialService.create(material);

        redirectAttributes.addFlashAttribute("successMessage", "Lưu chất liệu thành công!");
        redirectAttributes.addFlashAttribute("operation", "add"); // Thêm thông điệp động

        return "redirect:/admin/formmaterial"; // Chuyển hướng đến trang danh sách accounts
    }

    // edit product
    @GetMapping("editmaterial/{MaterialId}")
    public String EditMaterial(@PathVariable("MaterialId") Integer MaterialId, Model model) {
        Material material = materialService.findById(MaterialId);
        model.addAttribute("material", material);
        model.addAttribute("allMaterial", materialService.findAll());
        return "admin/pages/FormMaterial";
    }

    @GetMapping("/deletematerial/{MaterialId}")
    public String deleteMaterial(@PathVariable("MaterialId") Integer MaterialId,
            RedirectAttributes redirectAttributes) {
        try {
            Material material = materialService.findById(MaterialId);
            materialService.delete(material);
            // Đặt thông báo thành công vào redirectAttributes
            redirectAttributes.addFlashAttribute("deletesuccessMessage", "Xóa thành công!");
        } catch (Exception e) {
            // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
            redirectAttributes.addFlashAttribute("deleteerrorMessage", "Xóa thất bại: " + e.getMessage());
        }
        // Chuyển hướng người dùng đến trang hiển thị danh sách loại sản phẩm hoặc trang
        // chính của trang quản trị
        return "redirect:/admin/material";
    }
}
