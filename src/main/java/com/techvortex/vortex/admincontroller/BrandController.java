package com.techvortex.vortex.admincontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.techvortex.vortex.entity.Material;
import com.techvortex.vortex.service.BrandService;

@Controller
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    BrandService brandService;

    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
            + "/src/main/resources/static/assets/imagesadmin/";

    @GetMapping("/brand")
    public String showBrandList(Model model) {
        model.addAttribute("allbrand", brandService.findAll());
        model.addAttribute("brand", new Brand());
        return "admin/pages/Brand";
    }

    @GetMapping("/formbrand")
    public String FormBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/pages/FormBrand";
    }

    @PostMapping("/savebrand")
    public String createBrand(@ModelAttribute @Valid Brand brand, BindingResult bindingResult,
            @RequestParam("image") MultipartFile image, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("brand", brand);
            model.addAttribute("errorMessage", "Thương hiệu không được bỏ trống!");
            model.addAttribute("allbrand", brandService.findAll());
            return "admin/pages/FormBrand";
        }
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = image.getOriginalFilename();
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
                Files.write(fileNameAndPath, image.getBytes());
                brand.setBrandImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("brand", brand);
            model.addAttribute("errorMessagee", "anh khong duoc bo trong!");
            model.addAttribute("allbrand", brandService.findAll());
            return "admin/pages/FormBrand";
        }

        // Xử lý logic khi không có lỗi
        brandService.create(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Lưu thương hiệu thành công!");
        return "redirect:http://localhost:8080/admin/formbrand";
    }

    @GetMapping("/editbrand/{brandId}")
    public String EditMaterial(@PathVariable("brandId") Integer brandId, Model model) {
        Brand brand = brandService.findById(brandId);
        model.addAttribute("brand", brand);
        model.addAttribute("allbrand", brandService.findAll());
        return "admin/pages/FormBrand";
    }

    @GetMapping("/brand/delete/{brandId}")
    public String deleteBrand(@PathVariable("brandId") Integer brandId, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Brand brand = brandService.findById(brandId);
            if (brand != null) {
                brandService.delete(brand);
                // Set success message in redirectAttributes
                redirectAttributes.addFlashAttribute("deletesuccessMessage", "Xóa thành công!");
            } else {
                // Handle case where brand is not found by ID
                redirectAttributes.addFlashAttribute("deleteerrorMessage", "Không tìm thấy thương hiệu để xóa.");
            }
        } catch (Exception e) {
            // Set error message in redirectAttributes if an exception occurs
            redirectAttributes.addFlashAttribute("deleteerrorMessage", "Xóa thất bại: " + e.getMessage());
        }
        return "redirect:/admin/brand";
    }

}
